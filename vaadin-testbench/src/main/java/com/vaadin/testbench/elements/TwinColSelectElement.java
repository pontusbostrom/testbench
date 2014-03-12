/**
 * Copyright (C) 2012 Vaadin Ltd
 *
 * This program is available under Commercial Vaadin Add-On License 3.0
 * (CVALv3).
 *
 * See the file licensing.txt distributed with this software for more
 * information about licensing.
 *
 * You should have received a copy of the license along with this program.
 * If not, see <http://vaadin.com/license/cval-3>.
 */
package com.vaadin.testbench.elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.vaadin.testbench.By;

@ServerClass("com.vaadin.ui.TwinColSelect")
public class TwinColSelectElement extends AbstractSelectElement {

    private Select options;
    private Select selectedOptions;
    private WebElement deselButton;
    private WebElement selButton;
    private static org.openqa.selenium.By bySelect = By.tagName("select");
    private static org.openqa.selenium.By byButton = By.className("v-button");

    @Override
    public void init() {
        super.init();
        List<WebElement> selectElements = findElements(bySelect);
        options = new Select(selectElements.get(0));
        selectedOptions = new Select(selectElements.get(1));
        List<WebElement> buttons = findElements(byButton);
        selButton = buttons.get(0);
        deselButton = buttons.get(1);
    }

    public void deselectAll() {
        if (selectedOptions.isMultiple()) {
            if (selectedOptions.getAllSelectedOptions().size() != selectedOptions
                    .getOptions().size()) {
                for (int i = 0, l = selectedOptions.getOptions().size(); i < l; ++i) {
                    selectedOptions.selectByIndex(i);
                }
            }
            deselButton.click();
        }
        while (selectedOptions.getOptions().size() > 0) {
            selectedOptions.selectByIndex(0);
            deselButton.click();
        }
    }

    public void deselectByText(String text) {
        if (selectedOptions.isMultiple()) {
            selectedOptions.deselectAll();
        }
        selectedOptions.selectByVisibleText(text);
        deselButton.click();
    }

    /**
     * Functionality to find option texts of all currently selected options.
     * 
     * @return List of visible text for all selected options
     */
    public List<String> getSelectedOptions() {
        return getOptionsFromSelect(selectedOptions);
    }

    /**
     * Functionality to find all option texts.
     * 
     * @return List of visible text for all options
     */
    public List<String> getOptions() {
        List<String> optionTexts = getUnselectedOptions();
        optionTexts.addAll(getSelectedOptions());
        return optionTexts;
    }

    /**
     * Functionality to find option texts of all currently unselected options.
     * 
     * @return List of visible text for all unselected options
     */
    public List<String> getUnselectedOptions() {
        return getOptionsFromSelect(options);
    }

    public boolean isMultiple() {
        return true;
    }

    public void selectByText(String text) {
        options.selectByVisibleText(text);
        selButton.click();
    }

    private List<String> getOptionsFromSelect(Select select) {
        List<String> optionTexts = new ArrayList<String>();
        for (WebElement option : select.getOptions()) {
            optionTexts.add(option.getText());
        }
        return optionTexts;
    }
}