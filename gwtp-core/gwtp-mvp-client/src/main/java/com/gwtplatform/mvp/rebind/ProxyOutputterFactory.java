/**
 * Copyright 2010 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.mvp.rebind;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

/**
 * Factory used to instantiate the adequate {@link ProxyOutputter} given a
 * proxy interface.
 *
 * @author Philippe Beaudoin
 */
public class ProxyOutputterFactory {
  private final TypeOracle oracle;
  private final TreeLogger logger;
  private final ClassCollection classCollection;
  private final GinjectorInspector ginjectorInspector;
  private final PresenterInspector presenterInspector;

  public ProxyOutputterFactory(TypeOracle oracle, TreeLogger logger,
      ClassCollection classCollection,
      GinjectorInspector ginjectorInspector,
      PresenterInspector presenterInspector) {
    this.oracle = oracle;
    this.logger = logger;
    this.classCollection = classCollection;
    this.ginjectorInspector = ginjectorInspector;
    this.presenterInspector = presenterInspector;
  }

  /**
   * Instantiates the adequate {@link ProxyOutputter} given a
   * proxy interface.
   *
   * @param proxyInterface The proxy interface for which to create a {@link ProxyOutputter}.
   * @return The newly created {@link ProxyOutputter}.
   * @throws UnableToCompleteException If something goes wrong. An error will be logged.
   */
  public ProxyOutputter create(JClassType proxyInterface)
      throws UnableToCompleteException {

    CompositeProxyOutputter compositeProxyOutputter = null;
    if (isProxyPlace(proxyInterface) && isTabContentProxy(proxyInterface)) {
      compositeProxyOutputter =
          new CompositeProxyOutputter(oracle, logger, classCollection, ginjectorInspector, presenterInspector,
              null, ClassCollection.tabContentProxyPlaceImplClassName,
              ClassCollection.tabContentProxyImplClassName);
    }

    ProxyOutputterBase result = null;
    if (isProxyPlace(proxyInterface)) {
      result = new ProxyPlaceOutputter(oracle, logger, classCollection, ginjectorInspector, presenterInspector,
          compositeProxyOutputter);
      if (compositeProxyOutputter != null) {
        compositeProxyOutputter.add(result);
      }
    }
    if (isTabContentProxy(proxyInterface)) {
      result = new TabContentProxyOutputter(oracle, logger, classCollection, ginjectorInspector, presenterInspector,
          compositeProxyOutputter);
      if (compositeProxyOutputter != null) {
        compositeProxyOutputter.add(result);
      }
    }

    if (compositeProxyOutputter != null) {
      result = compositeProxyOutputter;
    }

    if (result == null) {
      result = new BasicProxyOutputter(oracle, logger, classCollection, ginjectorInspector, presenterInspector, null);
    }

    result.init(proxyInterface);
    return result;
  }

  private boolean isProxyPlace(JClassType proxyInterface) {
    return proxyInterface.isAssignableTo(classCollection.basePlaceClass);
  }

  private boolean isTabContentProxy(JClassType proxyInterface) {
    return proxyInterface.isAssignableTo(classCollection.tabContentProxyClass);
  }
}