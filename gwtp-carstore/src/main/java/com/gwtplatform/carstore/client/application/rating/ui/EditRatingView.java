/**
 * Copyright 2013 ArcBees Inc.
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

package com.gwtplatform.carstore.client.application.rating.ui;

import java.util.List;

import javax.inject.Inject;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.carstore.client.application.cars.car.CarRenderer;
import com.gwtplatform.carstore.client.application.rating.ui.EditRatingPresenter.MyView;
import com.gwtplatform.carstore.shared.dto.CarDto;
import com.gwtplatform.carstore.shared.dto.RatingDto;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class EditRatingView extends PopupViewWithUiHandlers<EditRatingUiHandlers> implements MyView, Editor<RatingDto> {
    interface Binder extends UiBinder<Widget, EditRatingView> {
    }

    interface Driver extends SimpleBeanEditorDriver<RatingDto, EditRatingView> {
    }

    @UiField
    IntegerBox rating;
    @UiField(provided = true)
    ValueListBox<CarDto> car;

    private final Driver driver;

    @Inject
    EditRatingView(Binder uiBinder,
                   Driver driver,
                   EventBus eventBus) {
        super(eventBus);

        car = new ValueListBox<>(new CarRenderer());
        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));

        driver.initialize(this);
    }

    @Override
    public void edit(RatingDto ratingDto) {
        if (ratingDto.getCar() == null) {
            ratingDto.setCar(car.getValue());
        }

        driver.edit(ratingDto);
    }

    @Override
    public void setAllowedCars(List<CarDto> carDtos) {
        car.setValue(carDtos.isEmpty() ? null : carDtos.get(0));
        car.setAcceptableValues(carDtos);
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent ignored) {
        getUiHandlers().onSave(driver.flush());
    }

    @UiHandler("close")
    void onCancelClicked(ClickEvent ignored) {
        getUiHandlers().onCancel();
    }
}
