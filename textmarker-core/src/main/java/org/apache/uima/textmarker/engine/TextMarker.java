/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.uima.textmarker.engine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

public class TextMarker {

  public static void apply(CAS cas, String script) throws IOException, InvalidXMLException,
          ResourceInitializationException, ResourceConfigurationException,
          AnalysisEngineProcessException, URISyntaxException {
    String viewName = cas.getViewName();
    URL aedesc = TextMarkerEngine.class.getResource("BasicEngine.xml");
    AnalysisEngine ae = wrapAnalysisEngine(aedesc, viewName);
    
    File scriptFile = File.createTempFile("TextMarker", ".tm");
    scriptFile.deleteOnExit();
    if (!script.startsWith("PACKAGE")) {
      script = "PACKAGE org.apache.uima.textmarker;\n" + script;
    }
    FileUtils.saveString2File(script, scriptFile);
    ae.setConfigParameterValue(TextMarkerEngine.SCRIPT_PATHS, new String[] { scriptFile
            .getParentFile().getAbsolutePath() });
    String name = scriptFile.getName().substring(0, scriptFile.getName().length() - 3);
    ae.setConfigParameterValue(TextMarkerEngine.MAIN_SCRIPT, name);
    ae.reconfigure();
    ae.process(cas);
    scriptFile.delete();
    ae.destroy();
  }

  public static AnalysisEngine wrapAnalysisEngine(URL descriptorUrl, String viewName)
          throws ResourceInitializationException, ResourceConfigurationException,
          InvalidXMLException, IOException, URISyntaxException {
    return wrapAnalysisEngine(descriptorUrl, viewName, false, Charset.defaultCharset().name());
  }

  public static AnalysisEngine wrapAnalysisEngine(URL descriptorUrl, String viewName, boolean textmarkerEngine, 
          String encoding) throws ResourceInitializationException, ResourceConfigurationException,
          InvalidXMLException, IOException, URISyntaxException {
    if (viewName.equals(CAS.NAME_DEFAULT_SOFA)) {
      XMLInputSource in = new XMLInputSource(descriptorUrl);
      ResourceSpecifier specifier = UIMAFramework.getXMLParser().parseResourceSpecifier(in);
      AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(specifier);
      return ae;
    } else {
      InputStream inputStream = null;
      if(textmarkerEngine) {
        inputStream = TextMarker.class.getResourceAsStream("AAEDBasicEngine.xml");
      } else {
        inputStream = TextMarker.class.getResourceAsStream("AAED.xml");
      }
      String aaedString = IOUtils.toString(inputStream, encoding);
      String absolutePath = descriptorUrl.toExternalForm();
      aaedString = aaedString.replaceAll("\\$\\{sofaName\\}", viewName);
      aaedString = aaedString.replaceAll("\\$\\{descriptorLocation\\}", absolutePath);
      File tempFile = File.createTempFile("TextMarkerAAED", ".xml");
      FileUtils.saveString2File(aaedString, tempFile);
      XMLInputSource in = new XMLInputSource(tempFile);
      ResourceSpecifier specifier = UIMAFramework.getXMLParser().parseResourceSpecifier(in);
      AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(specifier);
      tempFile.delete();
      return ae;
    }
  }

}
