/*******************************************************************************
 * Copyright 2018 Mountain Fog, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.mtnfog.test.renku;

import java.io.IOException;

import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.mtnfog.renku.RenkuLambdaFunction;

public class RenkuLambdaFunctionTest {

    @Test
    public void testLambdaFunctionHandler() throws IOException {
    	
    	RenkuLambdaFunction handler = new RenkuLambdaFunction();
    	
        Context ctx = createContext();
      
        Object output = handler.handleRequest("What language is this text in?", ctx);
     
        if (output != null) {
            System.out.println(output.toString());
        }
        
    }
    
    private Context createContext() {
    	
        TestContext ctx = new TestContext();
        ctx.setFunctionName("RenkuHandler");

        return ctx;
    }
    
}
