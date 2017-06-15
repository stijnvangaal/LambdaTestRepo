import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.infosupport.LambdaHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * Created by StijnG on 6/15/2017.
 */
public class LambdaHandlerTest {

    LambdaHandler handler;
    Context context;
    @Before
    public void testSetup(){
        handler = new LambdaHandler();
        context = new Context() {
            public String getAwsRequestId() {
                return "TestRequest";
            }

            public String getLogGroupName() {
                return "LambdaTestLog";
            }

            public String getLogStreamName() {
                return "LambdaTestLogStream";
            }

            public String getFunctionName() {
                return "handleRequest";
            }

            public String getFunctionVersion() {
                return "1.0";
            }

            public String getInvokedFunctionArn() {
                return "handlerequest";
            }

            public CognitoIdentity getIdentity() {
                return null;
            }

            public ClientContext getClientContext() {
                return null;
            }

            public int getRemainingTimeInMillis() {
                return 0;
            }

            public int getMemoryLimitInMB() {
                return 0;
            }

            public LambdaLogger getLogger() {
                return new LambdaLogger() {
                    public void log(String s) {

                    }
                };
            }
        };
    }

    @Test
    public void testCreateUser() throws IOException {
        InputStream inputStream = new ByteArrayInputStream( "{ \"firstName\" : \"stijn\", \"lastName\" : \"van Gaal\", \"httpMethod\" : \"POST\"}".getBytes() );
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        handler.handleRequest(inputStream, outputStream, context);
        String s = new String(outputStream.toByteArray());

        Assert.assertEquals("{\"body\":[{\"firstName\":\"stijn\",\"lastName\":\"van Gaal\"}],\"statusCode\":\"200\"}", s);
    }
}
