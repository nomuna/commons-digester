/* $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 


package org.apache.commons.digester.plugins;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.digester.Digester;

/**
 * Test cases for the declaration of custom rules for a plugin using
 * a separate class to define the rules.
 */

public class TestRuleInfo extends TestCase {
    /** Standard constructor */
    public TestRuleInfo(String name) { 
        super(name);
    }

    /** Set up instance variables required by this test case. */
    public void setUp() {}

    /** Return the tests included in this test suite. */
    public static Test suite() {

        return (new TestSuite(TestRuleInfo.class));

    }

    /** Tear down instance variables required by this test case.*/
    public void tearDown() {}
        
    // --------------------------------------------------------------- Test cases

    public void testRuleInfoExplicitClass() throws Exception {
        // * tests that custom rules can be declared on a 
        //   separate class by explicitly declaring the rule class.

        Digester digester = new Digester();
        PluginRules rc = new PluginRules();
        digester.setRules(rc);
        
        PluginDeclarationRule pdr = new PluginDeclarationRule();
        digester.addRule("root/plugin", pdr);
        
        PluginCreateRule pcr = new PluginCreateRule(Widget.class);
        digester.addRule("root/widget", pcr);
        digester.addSetNext("root/widget", "addChild");

        Container root = new Container();
        digester.push(root);
        
        try {
            digester.parse(
                TestAll.getInputStream(this, "test5a.xml"));
        }
        catch(Exception e) {
            throw e;
        }

        Object child;
        List children = root.getChildren();
        assertTrue(children != null);
        assertEquals(1, children.size());
        
        child = children.get(0);
        assertTrue(child != null);
        assertEquals(TextLabel2.class, child.getClass());
        TextLabel2 label = (TextLabel2) child;
        
        // id should not be mapped, label should
        assertEquals("anonymous", label.getId());
        assertEquals("std label", label.getLabel());
    }
    
    public void testRuleInfoExplicitMethod() throws Exception {
        // * tests that custom rules can be declared on a 
        //   separate class by explicitly declaring the rule class.
        //   and explicitly declaring the rule method name.

        Digester digester = new Digester();
        PluginRules rc = new PluginRules();
        digester.setRules(rc);
        
        PluginDeclarationRule pdr = new PluginDeclarationRule();
        digester.addRule("root/plugin", pdr);
        
        PluginCreateRule pcr = new PluginCreateRule(Widget.class);
        digester.addRule("root/widget", pcr);
        digester.addSetNext("root/widget", "addChild");

        Container root = new Container();
        digester.push(root);
        
        try {
            digester.parse(
                TestAll.getInputStream(this, "test5b.xml"));
        }
        catch(Exception e) {
            throw e;
        }

        Object child;
        List children = root.getChildren();
        assertTrue(children != null);
        assertEquals(1, children.size());
        
        child = children.get(0);
        assertTrue(child != null);
        assertEquals(TextLabel2.class, child.getClass());
        TextLabel2 label = (TextLabel2) child;
        
        // id should not be mapped, altlabel should
        assertEquals("anonymous", label.getId());
        assertEquals("alt label", label.getLabel());
    }
    
    public void testRuleInfoAutoDetect() throws Exception {
        // * tests that custom rules can be declared on a 
        //   separate class with name {plugin-class}RuleInfo,
        //   and they are automatically detected and loaded.

        Digester digester = new Digester();
        PluginRules rc = new PluginRules();
        digester.setRules(rc);
        
        PluginDeclarationRule pdr = new PluginDeclarationRule();
        digester.addRule("root/plugin", pdr);
        
        PluginCreateRule pcr = new PluginCreateRule(Widget.class);
        digester.addRule("root/widget", pcr);
        digester.addSetNext("root/widget", "addChild");

        Container root = new Container();
        digester.push(root);
        
        try {
            digester.parse(
                TestAll.getInputStream(this, "test5c.xml"));
        }
        catch(Exception e) {
            throw e;
        }

        Object child;
        List children = root.getChildren();
        assertTrue(children != null);
        assertEquals(1, children.size());
        
        child = children.get(0);
        assertTrue(child != null);
        assertEquals(TextLabel2.class, child.getClass());
        TextLabel2 label = (TextLabel2) child;
        
        // id should not be mapped, label should
        assertEquals("anonymous", label.getId());
        assertEquals("std label", label.getLabel());
    }
}
