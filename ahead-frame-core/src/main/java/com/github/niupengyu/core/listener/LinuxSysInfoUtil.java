package com.github.niupengyu.core.listener;

import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.core.util.StringUtil;

import java.util.Arrays;
import java.util.List;

public abstract class LinuxSysInfoUtil {


    public abstract List<String> cpuInfos() throws SysException;

    public abstract List<String> memInfos() throws SysException;

    public abstract List<String> ioInfos() throws SysException;

    public abstract List<String> netInfos() throws SysException;


    public long[] netInfo(String netName) throws SysException {
        List<String> cpu=netInfos();
        long inSize1 = 0, outSize1 = 0;
        for(String line:cpu){
            line = line.trim();
            System.out.println(line);
            if(line.startsWith(netName)){
                String[] temp = line.split("\\s+");
                System.out.println(Arrays.toString(temp));
                String str=temp[0].substring(netName.length());
                String str2=temp[8];
                if(StringUtil.isNull(str)){
                    str=temp[1];
                    str2=temp[9];
                }
                inSize1 = Long.parseLong(str);	//Receive bytes,单位为Byte
                outSize1 = Long.parseLong(str2);				//Transmit bytes,单位为Byte
                break;
            }
        }
        return new long[]{inSize1,outSize1};
    }

    public double ioUsage() throws SysException {
        List<String> infos=ioInfos();
        double ioUsage = 0.0f;
        int count = 0;
        for(int i=0;i<infos.size();i++){
            String line=infos.get(i);
            if(++count >= 4){
                String[] temp = line.split("\\s+");
                if(temp.length > 1){
                    float util =  Float.parseFloat(temp[temp.length-1]);
                    ioUsage = (ioUsage>util)?ioUsage:util;
                }
            }
        }
        if(ioUsage > 0){
            ioUsage /= 100;
        }
        return ioUsage;
    }

    public double memUsage()  throws Exception{
        List<String> infos=memInfos();
        double memUsage = 0.0f;
        int count = 0;
        double totalMem = 0, freeMem = 0;
        for(int i=0;i<infos.size();i++){
            String line=infos.get(i);
            String[] memInfo = line.split("\\s+");
            if(memInfo[0].startsWith("MemTotal")){
                totalMem = Long.parseLong(memInfo[1]);
            }
            if(memInfo[0].startsWith("MemFree")){
                freeMem = Long.parseLong(memInfo[1]);
            }
            memUsage = 1- freeMem/totalMem;
            if(++count == 2){
                break;
            }
        }
        return memUsage;
    }

    public long[] cpuTimeInfo() throws SysException {
        long idleCpuTime1 = 0, totalCpuTime1 = 0;
        //print(cpu);
        List<String> cpu=cpuInfos();
        if(!cpu.isEmpty()){
            String str=cpu.get(0);
            System.out.println(str);
            String[] line=str.split("\\s+");
            idleCpuTime1 = Long.parseLong(line[4]);
            for(String s : line){
                if(!s.equals("cpu")){
                    totalCpuTime1 += Long.parseLong(s);
                }
            }

        }
        return new long[]{idleCpuTime1,totalCpuTime1};
    }

    public double[] netUsage(double totalBandwidth,String netName) throws Exception {
        double netUsage = 0.0f;
        double curRate =0;
        long startTime = System.currentTimeMillis();
        long[] netSize=netInfo(netName);
        Thread.sleep(1000);
        long endTime = System.currentTimeMillis();
        long[] netSize1=netInfo(netName);
        System.out.println(Arrays.toString(netSize));
        System.out.println(Arrays.toString(netSize1));
        long inSize1 = netSize[0], outSize1 = netSize[1];	//分别为系统启动后空闲的CPU时间和总的CPU时间
        long inSize2 = netSize1[0], outSize2 = netSize1[1];	//分别为系统启动后空闲的CPU时间和总的CPU时间
        if(inSize1 != 0 && outSize1 !=0 && inSize2 != 0 && outSize2 !=0){
            double interval = (endTime - startTime)/1000;
            //网口传输速度,单位为bps
            curRate = (inSize2 - inSize1)*8/(1000000*interval);
            netUsage = (outSize2 - outSize1)*8/(1000000*interval);

            //netUsage = curRate/totalBandwidth;
        }
        return new double[]{curRate,netUsage};
    }

    public double cpuUsage() throws Exception {
        long[] cpuTime=cpuTimeInfo();
        Thread.sleep(1000);
        long[] cpuTime1=cpuTimeInfo();

        System.out.println(Arrays.toString(cpuTime));
        System.out.println(Arrays.toString(cpuTime1));
        long idleCpuTime1 = cpuTime[0], totalCpuTime1 = cpuTime[1];	//分别为系统启动后空闲的CPU时间和总的CPU时间
        long idleCpuTime2 = cpuTime1[0], totalCpuTime2 = cpuTime1[1];	//分别为系统启动后空闲的CPU时间和总的CPU时间
        double cpuUsage = 0;
        if(idleCpuTime1 != 0 && totalCpuTime1 !=0 && idleCpuTime2 != 0 && totalCpuTime2 !=0){
            double d1=idleCpuTime2 - idleCpuTime1;
            double d2=totalCpuTime2 - totalCpuTime1;
            System.out.println("d1 "+d1+" d2 "+d2);
            cpuUsage = 1 - d1/d2;
        }
        return cpuUsage;
    }


}
