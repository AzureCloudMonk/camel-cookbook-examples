/*
 * Copyright (C) Scott Cranton, Jakub Korab, and Christian Posta
 * https://github.com/CamelCookbook
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camelcookbook.extend.binding;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class MyBeanCamelTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new MyBeanRouteBuilder();
    }

    @Test
    public void testSayHello() throws Exception {
        final String response = template.requestBody("direct:normal", "Scott", String.class);

        assertEquals("Hello Scott", response);
    }

    @Test
    public void testSayHelloHipster() throws Exception {
        final String response = template.requestBody("direct:hipster", "Scott", String.class);

        assertEquals("Yo Scott", response);
    }

    @Test
    public void testSayHelloUndecided() throws Exception {
        String response = template.requestBodyAndHeader("direct:undecided", "Scott", "hipster", true, String.class);

        assertEquals("Yo Scott", response);

        response = template.requestBodyAndHeader("direct:undecided", "Scott", "hipster", false, String.class);

        assertEquals("Hello Scott", response);
    }
}
