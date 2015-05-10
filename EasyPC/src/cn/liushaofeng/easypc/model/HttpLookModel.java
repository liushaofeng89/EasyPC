package cn.liushaofeng.easypc.model;

/**
 * http look mode
 * @author liushaofeng
 * @date 2015-5-10
 * @version 1.0.0
 */
public class HttpLookModel
{
    private String sourceMAC;
    private String targetMAC;
    private String sourceIP;
    private String targetIP;
    private RequestDataModel requestData;
    private ResponseDataModel responseData;

    public String getSourceMAC()
    {
        return sourceMAC;
    }

    public void setSourceMAC(String sourceMAC)
    {
        this.sourceMAC = sourceMAC;
    }

    public String getTargetMAC()
    {
        return targetMAC;
    }

    public void setTargetMAC(String targetMAC)
    {
        this.targetMAC = targetMAC;
    }

    public String getSourceIP()
    {
        return sourceIP;
    }

    public void setSourceIP(String sourceIP)
    {
        this.sourceIP = sourceIP;
    }

    public String getTargetIP()
    {
        return targetIP;
    }

    public void setTargetIP(String targetIP)
    {
        this.targetIP = targetIP;
    }

    public RequestDataModel getRequestData()
    {
        return requestData;
    }

    public void setRequestData(RequestDataModel requestData)
    {
        this.requestData = requestData;
    }

    public ResponseDataModel getResponseData()
    {
        return responseData;
    }

    public void setResponseData(ResponseDataModel responseData)
    {
        this.responseData = responseData;
    }

}
