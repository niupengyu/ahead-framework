package com.github.niupengyu.socket.code;

import com.github.niupengyu.core.util.data.NumberUtil;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ByteArrayEncoder extends ProtocolEncoderAdapter {

 @Override
 public void encode(IoSession session, Object message,
                    ProtocolEncoderOutput out) throws Exception {
     IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
     //这个判断可根据自己的实际情况而定
     String msg=message.toString();
     //String msg = (String) message;
     byte[] bytes = msg.getBytes();
     byte[] heads = NumberUtil.int2Bytes(bytes.length);//调用方法将int数转为byte数组
     buf.put(heads);
     buf.put(bytes);
     buf.flip();//不可缺少
     out.write(buf);
 }

}
