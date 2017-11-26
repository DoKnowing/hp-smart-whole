package hp.smart.whole.core.hbase;

import com.google.protobuf.*;
import org.apache.hadoop.hbase.Coprocessor;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.coprocessor.CoprocessorService;

import java.io.IOException;

/**
 * @author: SMA
 * @date: 2017-10-18 11:49
 * @explain:
 */
public class KeywordsCoprocessorService implements CoprocessorService, Coprocessor, Service {

    @Override
    public void start(CoprocessorEnvironment env) throws IOException {

    }

    @Override
    public void stop(CoprocessorEnvironment env) throws IOException {

    }

    @Override
    public Service getService() {
        return this;
    }

    @Override
    public Descriptors.ServiceDescriptor getDescriptorForType() {
        return null;
    }

    @Override
    public void callMethod(Descriptors.MethodDescriptor methodDescriptor, RpcController rpcController, Message message, RpcCallback<Message> rpcCallback) {

    }

    @Override
    public Message getRequestPrototype(Descriptors.MethodDescriptor methodDescriptor) {
        return null;
    }

    @Override
    public Message getResponsePrototype(Descriptors.MethodDescriptor methodDescriptor) {
        return null;
    }
}
