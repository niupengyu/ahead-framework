package com.github.niupengyu.core.util.system;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

import com.github.niupengyu.core.util.data.NumberUtil;
import oshi.json.SystemInfo;
import oshi.json.hardware.CentralProcessor;
import oshi.json.hardware.GlobalMemory;
import oshi.json.hardware.HWDiskStore;
import oshi.json.hardware.HardwareAbstractionLayer;

/**
 * <p>
 * 类说明
 * </p>
 *
 */
public class SystemUsageUtil {

    private SystemInfo systemInfo = new SystemInfo();

    private HardwareAbstractionLayer hal;

    public SystemUsageUtil(){
        hal = systemInfo.getHardware();
    }

    /**
     * 获取内存的使用率
     *
     * @return 内存使用率 0.36
     */
    public double getMemoryUsage() {
        hal = systemInfo.getHardware();
        GlobalMemory memory = hal.getMemory();
        double available = memory.getAvailable();
        double total = memory.getTotal();
        //System.out.println("getMemoryUsage available={"+available+"},total={"+total+"}");
        double useRate = NumberUtil.decimalFormat(available/total,4);
        //NumberUtil.div(available, total, 2);
        return useRate;
    }

    /**
     * 获取CPU的使用率
     *
     * @return CPU使用率 0.36
     */
    public double getCpuUsage() {
        hal = systemInfo.getHardware();
        CentralProcessor processor = hal.getProcessor();
        double useRate = processor.getSystemCpuLoadBetweenTicks();
        //System.out.println("getCpuUsage useRate={"+useRate+"}");
        return NumberUtil.decimalFormat(useRate,  4);
    }


    /**
     * 获取磁盘的使用率
     *
     * @return CPU使用率 0.36
     */
    public double getDiskUsage() {
        if (isWindows()) {
            return getWinDiskUsage();
        }
        return getUnixDiskUsage();
    }


    /**
     * 判断系统是否为windows
     *
     * @return 是否
     */
    private static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    /**
     * 获取linux 磁盘使用率
     *
     * @return 磁盘使用率
     */
    private double getUnixDiskUsage() {
        String ioCmdStr = "df -h /";
        String resultInfo = runCommand(ioCmdStr);
        String[] data = resultInfo.split(" +");
        double total = Double.parseDouble(data[10].replace("%", ""));
        return total / 100;
    }

    public String serialNumber(){
        return hal.getComputerSystem().getSerialNumber();
    }

    /**
     * 获取linux 磁盘使用率
     *
     * @return 磁盘使用率
     */
    private double getWinDiskUsage() {

        hal = systemInfo.getHardware();
        HWDiskStore[] diskStores = hal.getDiskStores();
        double total = 0;
        double used = 0;
        if (diskStores != null && diskStores.length > 0) {
            for (HWDiskStore diskStore : diskStores) {
                double size = diskStore.getSize();
                double writeBytes = diskStore.getWriteBytes();
                total += size;
                used += writeBytes;
            }
        }
        return NumberUtil.decimalFormat(used/total, 4);
    }


    /**
     * 执行系统命令
     *
     * @param CMD 命令
     * @return 字符串结果
     */
    private String runCommand(String CMD) {
        StringBuilder info = new StringBuilder();
        try {
            Process pos = Runtime.getRuntime().exec(CMD);
            pos.waitFor();
            InputStreamReader isr = new InputStreamReader(pos.getInputStream());
            LineNumberReader lnr = new LineNumberReader(isr);
            String line;
            while ((line = lnr.readLine()) != null) {
                info.append(line).append("\n");
            }
        } catch (Exception e) {
            info = new StringBuilder(e.toString());
        }
        return info.toString();
    }


}
