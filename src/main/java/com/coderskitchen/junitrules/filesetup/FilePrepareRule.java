/**
 * Copyright 2013 Peter Daum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.coderskitchen.junitrules.filesetup;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This implementation of MethodRule returns a FilePrepareStatement which
 * creates and deletes the requested file structure for a test description.
 *
 * @author peter daum
 */
public class FilePrepareRule implements TestRule {

  private final Object classUnderTest;
  FileAnnotationParser fileAnnotationParser;

  /**
   * @param classUnderTest
   */
  public FilePrepareRule(Object classUnderTest) {
    this.classUnderTest = classUnderTest;
    fileAnnotationParser = new FileAnnotationParser();
  }

  @Override
  public Statement apply(Statement base, Description description) {
    fileAnnotationParser.setDescription(description);
    fileAnnotationParser.target = classUnderTest;
    final HashMap<String, HashSet<String>> structure = fileAnnotationParser.parseAnnotationsAndCreateStructure();
    return new FilePrepareStatement(base, structure);
  }
}
