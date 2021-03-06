/**
 * Copyright 2011 ArcBees Inc.
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

package com.gwtplatform.mvp.client.proxy;

import com.google.gwt.event.shared.EventHandler;

/**
 * Proxy should implement this class.
 */
interface PlaceRequestInternalHandler extends EventHandler {
    /**
     * Called when something has requested a new place. Should be implemented by
     * instances which can show the place.
     *
     * @param event The event.
     */
    void onPlaceRequest(PlaceRequestInternalEvent event);
}
