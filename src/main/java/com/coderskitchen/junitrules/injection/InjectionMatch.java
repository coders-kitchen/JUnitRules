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
package com.coderskitchen.junitrules.injection;

import java.lang.reflect.Field;

public class InjectionMatch {
  public final Field mockField;
  public final Field classField;

  public InjectionMatch(final Field mockField, final Field classField) {
    this.mockField = mockField;
    this.classField = classField;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final InjectionMatch that = (InjectionMatch) o;

    if (classField != null ? !classField.equals(that.classField) : that.classField != null) {
      return false;
    }
    if (mockField != null ? !mockField.equals(that.mockField) : that.mockField != null) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("InjectionMatch{");
    sb.append("classField=").append(classField);
    sb.append(", mockField=").append(mockField);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public int hashCode() {
    int result = mockField != null ? mockField.hashCode() : 0;
    result = 31 * result + (classField != null ? classField.hashCode() : 0);
    return result;
  }
}
