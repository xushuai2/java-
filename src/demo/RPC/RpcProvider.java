package demo.RPC;
/** 
 * RpcProvider 
 * 暴露服务  
 * @author 
 */  
public class RpcProvider {  
  
    public static void main(String[] args) throws Exception {  
        HelloService service = new HelloServiceImpl();  
        RpcFramework.export(service, 1234);  
    }  
  
}  
