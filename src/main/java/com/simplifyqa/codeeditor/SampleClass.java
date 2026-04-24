package com.simplifyqa.codeeditor;

import com.simplifyqa.abstraction.driver.IQAWebDriver;
import com.simplifyqa.abstraction.element.IQAWebElement;
import com.simplifyqa.pluginbase.argument.IArgument;
import com.simplifyqa.pluginbase.codeeditor.annotations.AutoInjectCurrentObject;
import com.simplifyqa.pluginbase.codeeditor.annotations.AutoInjectWebDriver;
import com.simplifyqa.pluginbase.codeeditor.annotations.SyncAction;
import com.simplifyqa.pluginbase.common.enums.TechnologyType;
import com.simplifyqa.pluginbase.common.models.Attribute;
import com.simplifyqa.pluginbase.common.models.SqaObject;
import com.simplifyqa.pluginbase.plugin.annotations.ObjectTemplate;
import com.simplifyqa.web.base.search.FindBy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import org.openqa.selenium.support.Color;
import com.simplifyqa.pluginbase.plugin.execution.IExecutionLogReporter;
import com.simplifyqa.pluginbase.common.enums.BrowserType;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
public class SampleClass {
    @AutoInjectWebDriver
    private IQAWebDriver driver;
    @AutoInjectCurrentObject
    private SqaObject currenObject;
    private static final Logger log = Logger.getLogger(SampleClass.class.getName());

    @SyncAction(uniqueId="MyProject-Sample-001", groupName="Click", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="This action belongs to WEB"))
    public boolean customSampleClick(String xpath) {
        this.driver.findElement(FindBy.xpath((String)xpath)).click();
        log.info("custom click is executed ");
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-002", groupName="Type Text", description="Save to db using db url", objectTemplate=@ObjectTemplate(name=TechnologyType.ANDROID, description="This action belongs to ANDROID"))
    public boolean customSampleTypeText(String xpath, String text) {
        this.driver.findElement(FindBy.xpath((String)xpath)).enterText(text);
        log.info("enter text is executed");
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-003", groupName="Type Text", objectTemplate=@ObjectTemplate(name=TechnologyType.IOS, description="This action belongs to IOS"))
    public boolean customSampleEnterTextWithJS(String xpathOfElement, String valueToEnter) {
        this.driver.findElement(FindBy.xpath((String)xpathOfElement)).enterText(valueToEnter);
        log.info("enter text is executed");
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-004", groupName="Generic", objectTemplate=@ObjectTemplate(name=TechnologyType.GENERIC, description="This action belongs to GENERIC"))
    public boolean customAddition(int ... ints) {
        this.driver.getGenericMethods().additionOfValues(ints);
        log.info("addition of values performed");
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-005", groupName="Generic", objectTemplate=@ObjectTemplate(name=TechnologyType.GENERIC, description="This action belongs to GENERIC"))
    public boolean getUnquieNumber(String suffix, IArgument value) {
        String uniqueNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSS"));
        log.info("Generated unique number: " + uniqueNumber);
        String generatedNumber = suffix.concat(" " + uniqueNumber);
        value.updateValue(generatedNumber);
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-006", groupName="Generic", objectTemplate=@ObjectTemplate(name=TechnologyType.GENERIC, description="This action belongs to GENERIC"))
    public boolean getCurrentDateWithFormat(String format, IArgument value) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate now = LocalDate.now();
            String currentDate = now.format(formatter);
            log.info("Generated Date: " + currentDate);
            value.updateValue(currentDate);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-007", groupName="Generic", objectTemplate=@ObjectTemplate(name=TechnologyType.GENERIC, description="This action belongs to GENERIC"))
    public boolean validateTwoParameterswithIgnoreCase(String value1, String value2) {
        try {
            String firstValue = value1.trim().toLowerCase();
            String secondValue = value2.trim().toLowerCase();
          if (firstValue.equalsIgnoreCase(secondValue)) {
            log.info("value1: " + firstValue + "value2 :" + secondValue);
            return true;
          }
          else
          {
            String firstValue1 = firstValue.replace('\u00A0', ' ');
            String secondValue2 = secondValue.replace('\u00A0', ' ');
            return firstValue1.equals(secondValue2);
          }
        } catch (Exception e) {
          log.info("value1: " + value1 + "value2 :" + value2);
          return false;
        }
      }

    public String getAttributeValue(String name) {
        try {
            List<Attribute> attributes = this.currenObject.attributes();
            for (Attribute attribute : attributes) {
                if (!attribute.name().equals(name)) continue;
                return attribute.value();
            }
        } catch (Exception e) {
            this.driver.getExecutionLogReporter().error(e.toString());
        }
        return null;
    }

    @SyncAction(uniqueId="MyProject-Sample-008", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="This action belongs to web"))
    public boolean elementIsExist() {
        try {
            String currentXpath = this.getAttributeValue("xpath");
            boolean a = this.driver.findElement(FindBy.xpath((String)currentXpath)).waitUntilElementPresent();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-009", groupName="Generic", objectTemplate=@ObjectTemplate(name=TechnologyType.GENERIC, description="This action belongs to GENERIC"))
    public boolean customDate(String UserInputDate, String format, IArgument value) {
        try {
            int additionalDate = Integer.parseInt(UserInputDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT"));
            ZonedDateTime newDate = now.plusDays(additionalDate);
            String currentDate = newDate.format(formatter);
            log.info("Generated Date in GMT: " + currentDate);
            value.updateValue(currentDate);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-010", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Verify The Number Of Rows"))
    public boolean validateNumberOfRows(String ExpectedNoOfRows) {
        try {
            int expectedCount = Integer.parseInt(ExpectedNoOfRows);
            String currentXpath = this.getAttributeValue("xpath");
            int a = this.driver.findElements(FindBy.xpath((String)currentXpath)).size();
            return a == expectedCount;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-011", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Verify The Number Of Rows"))
    public boolean salesforce() {
        try {
            String currentXpath = this.getAttributeValue("xpath");
            int a = this.driver.findElements(FindBy.xpath((String)currentXpath)).size();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-012", groupName="Generic", objectTemplate=@ObjectTemplate(name=TechnologyType.GENERIC, description="Remove Character from String"))
    public boolean removeChar(String param, String charToRemove, IArgument value) {
        try {
            String newValue = param.replace(charToRemove, "");
            value.updateValue(newValue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-013", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Selection of available Legal Clauses"))
    public boolean selectionAvailableLegalClauses(String value) {
        boolean bstatus = false;
        try {
            String aa = this.getAttributeValue("xpath");
            String bb = this.getAttributeValue("xpath1");
            String RightArrow = this.getAttributeValue("xpath2");
            String[] v = value.split("\\|");
            for (int j = 0; j < v.length; ++j) {
                String xpath2 = aa.replace("title", v[j]);
                this.driver.findElement(FindBy.xpath((String)xpath2)).click();
                Thread.sleep(300L);
                this.driver.findElement(FindBy.xpath((String)RightArrow)).click();
                Thread.sleep(300L);
                String xpath3 = bb.replace("title", v[j]);
                int count = this.driver.findElements(FindBy.xpath((String)xpath3)).size();
                if (count == 1) continue;
                bstatus = false;
                break;
            }
            bstatus = true;
        } catch (Exception e) {
            bstatus = false;
        }
        return bstatus;
    }

    @SyncAction(uniqueId="MyProject-Sample-014", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Verify if Field is available"))
    public boolean verifyIfFieldAvailable() {
        boolean bstatus = false;
        try {
            String a = this.getAttributeValue("xpath");
            int count = this.driver.findElements(FindBy.xpath((String)a)).size();
            if (count != 0) {
                bstatus = true;
            }
        } catch (Exception e) {
            bstatus = false;
        }
        return bstatus;
    }

    @SyncAction(uniqueId="MyProject-Sample-015", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Verify if Opportunity stage is changed to closed"))
    public boolean verifyIfOpportunityStageIsClosed() {
        boolean bStatus = false;
        try {
            String elementXpath = this.getAttributeValue("xpath");
            long startTime = System.currentTimeMillis();
            long maxDuration = 180000L;
            long interval = 10000L;
            while (System.currentTimeMillis() - startTime < maxDuration) {
                int count = this.driver.findElements(FindBy.xpath((String)elementXpath)).size();
                if (count != 0) {
                    return true;
                }
                this.driver.refresh();
                Thread.sleep(interval);
            }
        } catch (Exception e) {
            bStatus = false;
        }
        return bStatus;
    }

    @SyncAction(uniqueId="MyProject-Sample-017", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Verify if Given List Present"))
    public boolean verifyIfListAvailable(String value) {
        boolean bstatus = false;
        try {
            String aa = this.getAttributeValue("xpath");
            HashSet<String> dupList = new HashSet<String>();
            String[] v = value.split("\\|");
            for (int j = 0; j < v.length; ++j) {
                if (dupList.contains(v[j])) continue;
                dupList.add(v[j]);
                int stringCount = this.countDuplicateString(v[j], v);
                for (int i = 1; i <= stringCount; ++i) {
                    bstatus = false;
                    String xpath2 = aa.replace("replace", v[j]);
                    String updatedXpath = "(" + xpath2 + ")[" + i + "]";
                    int count = this.driver.findElements(FindBy.xpath((String)updatedXpath)).size();
                    if (count != 1) continue;
                    bstatus = true;
                }
                if (bstatus) continue;
                log.info(v[j] + " : This List Item Is Missing in UI.");
                return bstatus;
            }
            return bstatus;
        } catch (Exception e) {
            return false;
        }
    }

    public int countDuplicateString(String DupString, String[] sentence) {
        int count = 0;
        try {
            String[] arrayOfString = sentence;
            int i = sentence.length;
            for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
                String value = arrayOfString[b];
                if (!value.equals(DupString)) continue;
                ++count;
            }
            return count;
        } catch (Exception e) {
            return count;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-018", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Verify if Given List Name Present"))
    public boolean verifyIfListName(String value) {
        boolean bstatus = false;
        try {
            String aa = this.getAttributeValue("xpath");
            String[] v = value.split("\\|");
            for (int i = 1; i <= v.length; ++i) {
                bstatus = false;
                String xpath2 = aa.replace("replace", v[i - 1]);
                String updatedXpath = "(" + xpath2 + ")[" + i + "]";
                int count = this.driver.findElements(FindBy.xpath((String)updatedXpath)).size();
                if (count != 1) {
                    log.info(v[i] + " : This List Item Is Missing in UI.");
                    return bstatus;
                }
                bstatus = true;
            }
            return bstatus;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-019", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Store text from disabled filed"))
    public boolean getTextfromDisabledField(IArgument value) {
        try {
            String cXpath = this.getAttributeValue("xpath");
            String en = this.driver.executeScript("function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\"" + cXpath + "\");return a.innerText;", new Object[0]).toString();
            log.info("Stored text : " + en);
            value.updateValue(en);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-020", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Verify if AE Status is changed to Activated or Activation failed"))
    public boolean verifyIfAEIsActivated(String maxTimeOut) {
        boolean bStatus = false;
        try {
            String elementXpath = this.getAttributeValue("xpath");
            long startTime = System.currentTimeMillis();
            long maxDuration = Long.parseLong(maxTimeOut);
            long interval = 10000L;
            while (System.currentTimeMillis() - startTime < maxDuration) {
                int count = this.driver.findElements(FindBy.xpath((String)elementXpath)).size();
                if (count != 0) {
                    return true;
                }
                this.driver.refresh();
                Thread.sleep(interval);
            }
        } catch (Exception e) {
            bStatus = false;
        }
        return bStatus;
    }

    @SyncAction(uniqueId="MyProject-Sample-021", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Read and Store Element CSS Property"))
    public boolean getElementCSSProAndStore(String cssAttribute, IArgument value) {
        try {
            String elementXpath = this.getAttributeValue("xpath");
            String en = this.driver.executeScript("function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\"" + elementXpath + "\");return window.getComputedStyle(a)." + cssAttribute + ";", new Object[0]).toString();
            log.info("Stored Attribute Value : " + en);
            value.updateValue(en);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-022", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Verify if background color matched"))
    public boolean backColorValidation(String hexaCode) {
        try {
            String elementXpath = this.getAttributeValue("xpath");
            String en = this.driver.executeScript("function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\"" + elementXpath + "\");return window.getComputedStyle(a).backgroundColor;", new Object[0]).toString();
            String B = Color.fromString((String)en).asHex();
            return hexaCode.equalsIgnoreCase(B);
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-023", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Verify if text color matched"))
    public boolean textColorValidation(String hexaCode) {
        try {
            String elementXpath = this.getAttributeValue("xpath");
            String en = this.driver.executeScript("function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\"" + elementXpath + "\");return window.getComputedStyle(a).color;", new Object[0]).toString();
            String B = Color.fromString((String)en).asHex();
            return hexaCode.equalsIgnoreCase(B);
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-024", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Verify if Option is present in the dropdown"))
    public boolean verifyIfDPOptionIsAvailable(String valuetoCheck) {
        boolean bStatus = false;
        try {
            ArrayList<String> dPAllOptions = new ArrayList<String>();
            String[] Options = valuetoCheck.split("\\|");
            String elementXpath = this.getAttributeValue("xpath");
            int a = this.driver.findElements(FindBy.xpath((String)elementXpath)).size();
            if (a >= 1) {
                for (int i = 1; i <= a; ++i) {
                    String updatedXpath = "(" + elementXpath + ")[" + i + "]";
                    String value = this.driver.executeScript("function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\"" + updatedXpath + "\");return a.title;", new Object[0]).toString();
                    dPAllOptions.add(value);
                }
                String[] arrayOfString = Options;
                int j = Options.length;
                for (int b = 0; b < j; b = (int)((byte)(b + 1))) {
                    String option = arrayOfString[b];
                    if (!dPAllOptions.contains(option)) {
                        log.info("This : " + option + " not available in dropdown");
                        return false;
                    }
                    bStatus = true;
                }
            } else {
                log.info("Plaese check Object Attribute/Xpath");
                return false;
            }
            return bStatus;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-025", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Verify if Option is not present in the dropdown"))
    public boolean verifyIfDPOptionIsNotAvailable(String valuetoCheck) {
        boolean bStatus = false;
        try {
            ArrayList<String> dPAllOptions = new ArrayList<String>();
            String[] Options = valuetoCheck.split("\\|");
            String elementXpath = this.getAttributeValue("xpath");
            int a = this.driver.findElements(FindBy.xpath((String)elementXpath)).size();
            if (a >= 1) {
                for (int i = 1; i <= a; ++i) {
                    String updatedXpath = "(" + elementXpath + ")[" + i + "]";
                    String value = this.driver.executeScript("function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\"" + updatedXpath + "\");return a.title;", new Object[0]).toString();
                    dPAllOptions.add(value);
                }
                String[] arrayOfString = Options;
                int j = Options.length;
                for (int b = 0; b < j; b = (int)((byte)(b + 1))) {
                    String option = arrayOfString[b];
                    if (dPAllOptions.contains(option)) {
                        log.info("This : " + option + " is available in dropdown");
                        return false;
                    }
                    bStatus = true;
                }
            } else {
                log.info("Plaese check Object Attribute/Xpath");
                return false;
            }
            return bStatus;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-026", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Select multiple services based on products"))
    public boolean selectMultipleService(String products) {
        boolean bStatus = false;
        try {
            String[] Options = products.split("\\|");
            String elementXpath = this.getAttributeValue("xpath");
            for (int i = 1; i <= Options.length; ++i) {
                String updateXpath = elementXpath.replace("#replace", Options[i]);
                boolean a = this.driver.findElement(FindBy.xpath((String)updateXpath)).click();
                if (!a) {
                    log.info("Unable to Selcet Service for this product " + Options[i] + ".");
                    return false;
                }
                bStatus = true;
            }
            return bStatus;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-029", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Read and Store Element CSS Property"))
    public boolean getElementProAndStore(String elementPro, IArgument value) {
        try {
            String elementXpath = this.getAttributeValue("xpath");
            String en = this.driver.executeScript("function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\"" + elementXpath + "\");return a." + elementPro + ";", new Object[0]).toString();
            log.info("Stored Attribute Value : " + en);
            value.updateValue(en);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-030", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Dynamic Click"))
    public boolean dynamicClick(String ... replacable) {
        String elementXpath;
        boolean bStatus = false;
        String updatedXpath = elementXpath = this.getAttributeValue("xpath");
        for (int i = 0; i < replacable.length; ++i) {
            String updatedReplaceValue = "#replace" + (i + 1);
            updatedXpath = updatedXpath.replace(updatedReplaceValue, replacable[i]);
        }
        boolean a = this.driver.findElement(FindBy.xpath((String)updatedXpath)).click();
        if (!a) {
            log.info("Unable to replace the value, The current Attribute property is " + updatedXpath);
            return false;
        }
        bStatus = true;
        return bStatus;
    }

    @SyncAction(uniqueId="MyProject-Sample-031", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Concetenate all given string"))
    public boolean concetenateString(IArgument store, String ... replacable) {
        boolean bStatus = false;
        Object updatedXpath = replacable[0];
        for (int i = 1; i < replacable.length; ++i) {
            updatedXpath = (String)updatedXpath + replacable[i];
        }
        store.updateValue((String)updatedXpath);
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-032", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Multiple Click"))
    public boolean multipleClick() {
        boolean bStatus = false;
        String elementXpath = this.getAttributeValue("xpath");
        int len = this.driver.findElements(FindBy.xpath((String)elementXpath)).size();
        if (len >= 1) {
            Object updatedXpath = elementXpath;
            for (int i = 1; i <= len; ++i) {
                updatedXpath = "(" + elementXpath + ")[1]";
                boolean a = this.driver.findElement(FindBy.xpath((String)updatedXpath)).click();
                if (!a) {
                    log.info("Unable to click " + (String)updatedXpath);
                    return false;
                }
                bStatus = true;
            }
        } else {
            return true;
        }
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-033", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Concetenate all given string"))
    public boolean concateMultipleValueWithDelimeter(IArgument store, String delimeter, String ... replacable) {
        boolean bStatus = false;
        Object newValue = replacable[0];
        for (int i = 1; i < replacable.length; ++i) {
            newValue = (String)newValue + delimeter + replacable[i];
        }
        store.updateValue((String)newValue);
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-034", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Type text in VLAN and Validate"))
    public boolean enterRandomNumberandValidate(String duplicateXpath, String duplicationMsg, IArgument store) {
        boolean bStatus = false;
        boolean v = false;
        String elementXpath = this.getAttributeValue("xpath");
        Random random = new Random();
        int min = 2;
        int max = 4094;
        try {
            for (int i = 0; i >= 0; ++i) {
                int randomNumber = random.nextInt(max - min + 1) + min;
                String ranNum = Integer.toString(randomNumber);
                this.driver.findElement(FindBy.xpath((String)elementXpath)).clearText();
                this.driver.findElement(FindBy.xpath((String)elementXpath)).enterText(ranNum);
                int len = this.driver.findElements(FindBy.xpath((String)duplicateXpath)).size();
                if (len == 1) {
                    v = this.driver.findElement(FindBy.xpath((String)duplicateXpath)).validatePartialText(duplicationMsg);
                    this.driver.findElement(FindBy.xpath((String)"//button[text()='Previous']")).click();
                }
                if (!v) {
                    bStatus = true;
                    store.updateValue(ranNum);
                    System.out.println("Random number between " + min + " and " + max + ": " + randomNumber);
                    break;
                }
                bStatus = false;
            }
        } catch (Exception e) {
            this.driver.getExecutionLogReporter().error(e.toString());
        }
        return bStatus;
    }

    @SyncAction(uniqueId="MyProject-Sample-035", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Addition of Multiple Values"))
    public boolean addMultipleValues(IArgument store, String ... replacable) {
        try {
            double newValue = 0.0;
            String strValue = "null";
            for (int i = 0; i < replacable.length; ++i) {
                String cleaned = replacable[i].replaceAll("(USD|EUR)", "").replaceAll("\\s+", " ").trim();
                double value = Double.parseDouble(cleaned);
                BigDecimal bd = new BigDecimal(newValue += value).setScale(2, RoundingMode.HALF_UP);
                strValue = bd.toPlainString();
                System.out.println("Using BigDecimal: " + strValue);
            }
            store.updateValue(strValue);
            return true;
        } catch (Exception e) {
            this.driver.getExecutionLogReporter().error(e.toString());
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-036", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Division"))
    public boolean division(String divisor, String devidend, IArgument store) {
        try {
            int div = Integer.parseInt(divisor);
            String cleaned = devidend.replaceAll("(USD|EUR)", "").replaceAll("\\s+", " ").trim();
            double dev = Double.parseDouble(cleaned);
            double quotient = dev / (double)div;
            BigDecimal bd = new BigDecimal(quotient).setScale(2, RoundingMode.HALF_UP);
            String result = bd.toPlainString();
            store.updateValue(result);
            return true;
        } catch (Exception e) {
            this.driver.getExecutionLogReporter().error(e.toString());
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-037", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Subtract Two numbers"))
    public boolean subtractTwoNumbers(String value1, String value2, IArgument store) {
        try {
            String cleanedValue1 = value1.replaceAll("(USD|EUR)", "").replaceAll("\\s+", " ").trim();
            String cleanedValue2 = value2.replaceAll("(USD|EUR)", "").replaceAll("\\s+", " ").trim();
            double douValue1 = Double.parseDouble(cleanedValue1);
            double douValue2 = Double.parseDouble(cleanedValue2);
            double finalValue = douValue1 - douValue2;
            BigDecimal bd = new BigDecimal(finalValue).setScale(2, RoundingMode.HALF_UP);
            String result = bd.toPlainString();
            store.updateValue(result);
            return true;
        } catch (Exception e) {
            this.driver.getExecutionLogReporter().error(e.toString());
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-038", groupName="Web", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="Addition of Filed Values"))
    public boolean addMultipleFieldValues(String elementPro, IArgument store) {
        try {
            double newValue = 0.0;
            String strValue = "null";
            String elementXpath = this.getAttributeValue("xpath");
            int len = this.driver.findElements(FindBy.xpath((String)elementXpath)).size();
            if (len >= 1) {
                for (int i = 1; i <= len; ++i) {
                    String updatedXpath = "(" + elementXpath + ")[" + i + "]";
                    String en = this.driver.executeScript("function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\"" + updatedXpath + "\");return a." + elementPro + ";", new Object[0]).toString();
                    log.info("Stored Attribute Value : " + en);
                    String cleaned = en.replaceAll("(USD|EUR|,)", "").replaceAll("\\s+", " ").trim();
                    double value = Double.parseDouble(cleaned);
                    BigDecimal bd = new BigDecimal(newValue += value).setScale(2, RoundingMode.HALF_UP);
                    strValue = bd.toPlainString();
                    System.out.println("Using BigDecimal: " + strValue);
                }
            } else {
                return false;
            }
            store.updateValue(strValue);
            return true;
        } catch (Exception e) {

            this.driver.getExecutionLogReporter().error(e.toString());
            return false;
        }
    }

    private IExecutionLogReporter logReporter;
    @SyncAction(uniqueId = "MyProject-Sample-040", groupName = "Assertions", objectTemplate = @ObjectTemplate(name = TechnologyType.WEB, description = "This action reads data from pdf"), objectRequired = false)
    public boolean readFromPdf(String pdfPath, String pageNumber1,String valueToBeCompared,IArgument pdfDataRuntime,IArgument comparedResult) {
         int pageNumber=Integer.valueOf(pageNumber1);
      // String result= readPage( filepath , pagen);


       String pageText = "";

       try (PDDocument document = PDDocument.load(new File(pdfPath))) {

           if (pageNumber < 1 || pageNumber > document.getNumberOfPages()) {
               throw new IllegalArgumentException("Invalid page number!");
           }

           PDFTextStripper stripper = new PDFTextStripper();

           // Set start & end page as same
           stripper.setStartPage(pageNumber);
           stripper.setEndPage(pageNumber);

           pageText = stripper.getText(document);

       } catch (Exception e) {
           e.printStackTrace();
       }


       if(pageText.contains(valueToBeCompared)){
        comparedResult.updateValue(valueToBeCompared+ " - is present in Base String ");
        log.info("Verified "+valueToBeCompared+ " is presnt in BaseString "+pageText);
        pdfDataRuntime.updateValue(pageText);
        return true;
    }else{
        pdfDataRuntime.updateValue(pageText);
        comparedResult.updateValue(valueToBeCompared+ " - is not present in Base String ");
        return false; 
    }


    }



    @SyncAction(uniqueId = "get file path", groupName = "Assertions",
        objectTemplate = @ObjectTemplate(name = TechnologyType.WEB,
        description = "This action retrieves the full file path of the downloaded file"),
        objectRequired = false)
public boolean getFileName(IArgument runtime) {

    if (driver.getBrowserType() == BrowserType.CHROME
                || driver.getBrowserType() == BrowserType.CHROME_HEADLESS) {

            driver.launchUrlAndSwitch("about:blank");
            driver.launchApplication("chrome://downloads");

            try {
                String script =
                        "var manager = document.querySelector('downloads-manager');" +
                                "var list = manager.shadowRoot.querySelector('#downloadsList');" +
                                "var item = list.items[0];" +
                                "return item.filePath || item.file_path || item.file_name || 'Property not found';";

                Object result = driver.executeScript(script);

                if (result != null) {
                    String filePath = result.toString();
                    runtime.updateValue(filePath);
                    return true;
                }
            } catch (Exception e) {
                System.err.println("Error fetching full file path (Chrome): " + e.getMessage());
            }

        } else if (driver.getBrowserType() == BrowserType.EDGE) {

            driver.launchUrlAndSwitch("about:blank");
            driver.launchApplication("edge://downloads");

            try {
                String script =
                        "var manager = document.querySelector('downloads-manager');" +
                                "var list = manager.shadowRoot.querySelector('#downloadsList');" +
                                "var item = list.items[0];" +
                                "return item.filePath || item.file_path || item.file_name || 'Property not found';";

                Object result = driver.executeScript(script);
                if (result != null) {
                    String filePath = result.toString();
                    runtime.updateValue(filePath);
                    return true;
                }
            } catch (Exception e) {
                System.err.println("Error fetching full file path (Edge): " + e.getMessage());
            }
        }
    return false;
    }
    public String generateUnquieNumber() {
        String uniqueNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSS"));
        return uniqueNumber;
      }
    private String getTitleByXpath(String xpath) {
        String script =
            "function getElementByXpath(path) {" +
            "return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}" +
            "var el = getElementByXpath(\"" + xpath + "\");" +
            "return el ? el.title : '';";
     
        Object result = this.driver.executeScript(script);
        return result != null ? result.toString() : "";
    } 
    
@SyncAction(uniqueId = "MyProject-Sample-041", groupName = "Web", objectTemplate = @ObjectTemplate(name = TechnologyType.WEB, description = "Verify and Fill the value"))
public Boolean verifyFiledStoreandPopulateValue() {
    try {
      String Received_previously = getAttributeValue("Received_previously");// Received previously
      String Remaining = getAttributeValue("Remaining");
      String Received_now = getAttributeValue("Received_now");
      String SerialNumber = getAttributeValue("SerialNumber");
      int a = this.driver.findElements(FindBy.xpath(Remaining)).size();
      if (a >= 1) {
        // table/thead/tr/th[@title='Remaining']/../../following-sibling::tbody/tr/td
        for (int i = 1; i <= a; i++) {
          String serialNum = generateUnquieNumber();
          String updated_Received_nowXpath = "(" + Received_now + ")[" + i + "]";
          String updated_RemainingXpath = "(" + Remaining + ")[" + i + "]";
          String Updated_ReceivedpreviouslyXpath = "(" + Received_previously + ")[" + i + "]";
          String updated_SerialNumber = "(" + SerialNumber + ")[" + i + "]";
          int countSerialNumber = this.driver.findElements(FindBy.xpath(updated_SerialNumber)).size();
          // table/thead/tr/th[@title='Remaining']/../../following-sibling::tbody/tr/td
          // textarea[contains(@name,'input')]
          String remainingValue = getTitleByXpath(updated_RemainingXpath);
          String receivedPreviouslyValue = getTitleByXpath(Updated_ReceivedpreviouslyXpath);
          this.driver.findElement(FindBy.xpath(updated_Received_nowXpath)).clearText();
          if (receivedPreviouslyValue.equals("0")) {
            Double val1 = Double.parseDouble(remainingValue);
            Double res1 = val1 / 2;
            String mainRes = Double.toString(res1);
 
            this.driver.findElement(FindBy.xpath(updated_Received_nowXpath)).enterText(mainRes);
          } else {
            this.driver.findElement(FindBy.xpath(updated_Received_nowXpath)).enterText(remainingValue);
 
            if (countSerialNumber == 1) {
              this.driver.findElement(FindBy.xpath(updated_SerialNumber)).enterText(serialNum);
            }
          }
          Thread.sleep(2000);
          if (countSerialNumber == 1) {
            this.driver.findElement(FindBy.xpath(updated_SerialNumber)).enterText(serialNum);
          }
        }
        Thread.sleep(2000);
      }
      return true;
    } catch (Exception e) {
      // TODO: handle exception
      return false;
    }
  }
 
  @SyncAction(uniqueId = "MyProject-Sample-042", groupName = "Web", objectTemplate = @ObjectTemplate(name = TechnologyType.WEB, description = "verify Staus Bar Field"))
  public boolean verifyStausBarField(String valuetoCheck) {
    boolean flag = false;
    try {
      String elementXpath = getAttributeValue("xpath"); // InputXpath
      int a = this.driver.findElements(FindBy.xpath(elementXpath)).size();
      String[] Options = valuetoCheck.split(",");
      int len = Options.length;
      //int len = replacable.length;
      if (a >= 1 && a == len) {
        for (int i = 0; i < len; i++) {
          int uiIndex = i + 1;
          String input = Options[i];
          String updatedXpath = "(" + elementXpath + ")[" + uiIndex + "]";
          String value = this.driver.executeScript(
              "function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\""
                  + updatedXpath + "\");return a.title;",
              new Object[0]).toString();
          if (input.equalsIgnoreCase(value)) {
            flag = true;
          } else {
            flag = false;
            return false;
          }
        }
        return flag;
      } else {
        return false;
      }
 
    } catch (Exception e) {
      // TODO: handle exception
      return false;
    }
  }
  @SyncAction(uniqueId = "MyProject-Sample-043", groupName = "Generic", objectTemplate = @ObjectTemplate(name = TechnologyType.GENERIC, description = "Generates Ist day of the month date"))
    public boolean getFirstDayAfterAddingMonths(String monthsToAdd,String format, IArgument value) {
 
        try
        {
          int additionalMonth = Integer.parseInt(monthsToAdd);
          LocalDate currentDate = LocalDate.now();
          // Add months
          LocalDate updatedDate = currentDate.plusMonths(additionalMonth);
          LocalDate firstDayOfMonth = updatedDate.withDayOfMonth(1);
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
          String expectedDate =  firstDayOfMonth.format(formatter);
          log.info("Generated Date in GMT: " + expectedDate);
          value.updateValue(expectedDate);
          return true;
        }
        catch(DateTimeParseException e) {
          return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-044", groupName="Generic", objectTemplate=@ObjectTemplate(name=TechnologyType.GENERIC, description="This action belongs to GENERIC"))
    public boolean validateTwoDatesswithFormat(String value1, String value2, String format) {
    try {

        String firstValue = value1.trim().replace('\u00A0', ' ');
        String secondValue = value2.trim().replace('\u00A0', ' ');

        try {
            firstValue = firstValue.replace("/", ".").replace("-", ".");
            secondValue = secondValue.replace("/", ".").replace("-", ".");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

            LocalDate date1 = LocalDate.parse(firstValue, formatter);
            LocalDate date2 = LocalDate.parse(secondValue, formatter);

            firstValue = date1.format(formatter);
            secondValue = date2.format(formatter);

        } catch (DateTimeParseException e) {
            log.info("Date parsing failed. Proceeding with string comparison.");
        }

        if (firstValue.equalsIgnoreCase(secondValue)) {
            log.info("value1: " + firstValue + " value2 :" + secondValue);
            return true;
        } else {
            return firstValue.equalsIgnoreCase(secondValue);
        }

        } catch (Exception e) {
            log.info("value1: " + value1 + " value2 :" + value2);
            return false;
        }
    }
        
    @SyncAction(uniqueId = "MyProject-Sample-045", groupName = "Web", objectTemplate = @ObjectTemplate(name = TechnologyType.WEB, description = "Validate Value"))
    public boolean validateValue(String valueToValidate)
    {
        try
        { 
            double newValue = 0.0;
            String currentxpath = getAttributeValue("xpath");
            String en = this.driver.executeScript(
                "function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\""
                    + currentxpath + "\");return a.value;",
                new Object[0]).toString();
                double value = Double.parseDouble(en);
                BigDecimal bd = new BigDecimal(newValue += value).setScale(2, RoundingMode.HALF_UP);
                String strValue = bd.toPlainString();
                if(valueToValidate.equals(strValue))
                {
                return true;
                }
            return false;
        }
        catch(Exception e)
        {
        return false;
        }
    }
    @SyncAction(uniqueId = "MyProject-Sample-046", groupName = "Web", objectTemplate = @ObjectTemplate(name = TechnologyType.WEB, description = "Validate Gross Total Value"))
    public boolean validateGrossTotalValue(String netValue, String taxValue){
        try{
            String objectXpath = getAttributeValue("xpath");
            String GrossTotal = this.driver.executeScript(
                "function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\""
                    + objectXpath + "\");return a.value;",
                    new Object[0]).toString();
            //String GrossTotal = en.replaceAll("(USD|EUR)", "").replaceAll(",", ".").trim();
            double value = parseCurrency(GrossTotal);
            double NetValue = parseCurrency(netValue);
            double TaxValue = parseCurrency(taxValue);
            double CalculatedGrossValue = NetValue + TaxValue;
            if(Math.abs(CalculatedGrossValue - value) < 0.01){
                log.info("Net Value: "+netValue+ " Tax Value: "+taxValue+" Gross Total Value: "+CalculatedGrossValue);
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public double parseCurrency(String value) {

        value = value.replaceAll("(USD|EUR)", "").trim();
    
        if(value.contains(",") && value.contains(".")){
            if(value.lastIndexOf(",") > value.lastIndexOf(".")){
                // European format
                value = value.replace(".", "").replace(",", ".");
            }else{
                // US format
                value = value.replace(",", "");
            }
        }
        else if(value.contains(",")){
            value = value.replace(",", ".");
        }
    
        return Double.parseDouble(value);
    }

    @SyncAction(uniqueId = "MyProject-Sample-047", groupName = "Web", objectTemplate = @ObjectTemplate(name = TechnologyType.WEB, description = "Verify if Query stage is changed to Completed"))
    public boolean verifyIfQueryStageIsCompleted() {
      boolean bStatus = false;
      try {
        String elementXpath = this.getAttributeValue("xpath");//td[text()='Completed']
        String Jobs = this.getAttributeValue("jobXpath");
        String searchedJobHeader = "//h2[contains(text(),'Selected job:')]";
        String availableJobsHeader = "//td[@class='pbTitle']//h2[text()='Available jobs']";
        long startTime = System.currentTimeMillis();
        long maxDuration = 300000L;
        long interval = 20000L;
        while (System.currentTimeMillis() - startTime < maxDuration) {
          int count = this.driver.findElements(FindBy.xpath((String) elementXpath)).size();
          if (count != 0) {
            return true;
          }
          this.driver.refresh();
          Thread.sleep(interval);
          this.driver.findElement(FindBy.xpath((String) availableJobsHeader)).scrollIntoElement();
          Thread.sleep(1000);
          this.driver.findElement(FindBy.xpath((String) Jobs)).click();
          Thread.sleep(2000);
          this.driver.findElement(FindBy.xpath((String) searchedJobHeader)).scrollIntoElement();
        }
      } catch (Exception e) {
        bStatus = false;
      }
      return bStatus;
    }

    @SyncAction(uniqueId = "validate-table-reference-locations-strict-order",groupName = "Assertions",objectTemplate = @ObjectTemplate(name = TechnologyType.WEB,description = "Validate table rows in strict order using Reference, Location A and Location Z"))
    public boolean validateTableRowsStrictOrder(String expectedRowsInput) {
        try {
            String tableXpath = this.getAttributeValue("xpath");
            if (tableXpath == null || tableXpath.trim().isEmpty()) {
                log.info("Table xpath is empty.");
                this.driver.getExecutionLogReporter().info("Table xpath is empty.");
                return false;
            }
            if (expectedRowsInput == null || expectedRowsInput.trim().isEmpty()) {
                log.info("Expected input is empty.");
                this.driver.getExecutionLogReporter().info("Expected input is empty.");
                return false;
            }
            // Parse expected rows:
            // Reference|Description|Location A|Location Z#Reference|Description|Location A|Location Z
            String[] expectedRows = expectedRowsInput.split("#", -1);
            List<String[]> parsedExpected = new ArrayList<String[]>();
            for (int i = 0; i < expectedRows.length; i++) {
                String row = expectedRows[i] == null ? "" : expectedRows[i].trim();
                if (row.isEmpty()) {
                    continue;
                }
                // -1 keeps empty tokens (important for blank Description/Location A/Location Z)
                String[] cells = row.split("\\|", -1);
                if (cells.length != 4) {
                    log.info("Invalid expected row format at index " + (i + 1)
                            + ". Expected: Reference|Description|Location A|Location Z, Actual: " + row);
                    this.driver.getExecutionLogReporter().info("Invalid expected row format at index " + (i + 1)
                            + ". Expected: Reference|Description|Location A|Location Z, Actual: " + row);
                    return false;
                }
                String reference = cells[0] == null ? "" : cells[0].replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
                String description = cells[1] == null ? "" : cells[1].replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
                String locationA = cells[2] == null ? "" : cells[2].replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
                String locationZ = cells[3] == null ? "" : cells[3].replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
                if (reference.isEmpty()) {
                    log.info("Reference cannot be empty in expected row " + (i + 1));
                    this.driver.getExecutionLogReporter().info("Reference cannot be empty in expected row " + (i + 1));
                    return false;
                }
                parsedExpected.add(new String[]{reference, description, locationA, locationZ});
            }
            if (parsedExpected.isEmpty()) {
                log.info("No valid expected rows found.");
                this.driver.getExecutionLogReporter().info("No valid expected rows found.");
                return false;
            }
            // Only actual data rows (ignore footer total row)
            String rowXpath = tableXpath + "//tbody//c-design-tool-table-row";
            List<IQAWebElement> actualRows = this.driver.findElements(FindBy.xpath(rowXpath));
            if (actualRows == null || actualRows.isEmpty()) {
                log.info("No actual data rows found in table.");
                this.driver.getExecutionLogReporter().info("No actual data rows found in table.");
                return false;
            }
            // Strict order/count check
            if (actualRows.size() != parsedExpected.size()) {
                log.info("Row count mismatch. Expected: " + parsedExpected.size() + ", Actual: " + actualRows.size());
                this.driver.getExecutionLogReporter().info("Row count mismatch. Expected: " + parsedExpected.size() + ", Actual: " + actualRows.size());
                return false;
            }
            String cellTextScript =
                    "function getElementByXpath(path){return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}" +
                    "var el = getElementByXpath(arguments[0]);" +
                    "if(!el) return '';" +
                    "return (el.innerText || el.textContent || '').replace(/\\u00A0/g,' ').replace(/\\s+/g,' ').trim();";
            for (int i = 1; i <= actualRows.size(); i++) {
                String baseRowXpath = "(" + rowXpath + ")[" + i + "]";
                String refXpath = baseRowXpath + "//td[@data-label='Reference']";
                String descXpath = baseRowXpath + "//td[@data-label='Description']";
                String locAXpath = baseRowXpath + "//td[@data-label='Location A']";
                String locZXpath = baseRowXpath + "//td[@data-label='Location Z']";
                String actualReference = String.valueOf(this.driver.executeScript(cellTextScript, refXpath));
                String actualDescription = String.valueOf(this.driver.executeScript(cellTextScript, descXpath));
                String actualLocationA = String.valueOf(this.driver.executeScript(cellTextScript, locAXpath));
                String actualLocationZ = String.valueOf(this.driver.executeScript(cellTextScript, locZXpath));
                actualReference = actualReference.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
                actualDescription = actualDescription.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
                actualLocationA = actualLocationA.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
                actualLocationZ = actualLocationZ.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
                String expectedReference = parsedExpected.get(i - 1)[0];
                String expectedDescription = parsedExpected.get(i - 1)[1];
                String expectedLocationA = parsedExpected.get(i - 1)[2];
                String expectedLocationZ = parsedExpected.get(i - 1)[3];
                // Reference: contains (case-sensitive), as requested
                if (!actualReference.contains(expectedReference)) {
                    log.info("Row " + i + " Reference mismatch. Expected contains: [" + expectedReference
                            + "] Actual: [" + actualReference + "]");
                    this.driver.getExecutionLogReporter().info("Row " + i + " Reference mismatch. Expected contains: [" + expectedReference
                            + "] Actual: [" + actualReference + "]");
                    return false;
                }
                // Description:
                // - expected empty => actual must be empty
                // - expected non-empty => actual should contain expected
                if (expectedDescription.isEmpty()) {
                    if (!actualDescription.isEmpty()) {
                        log.info("Row " + i + " Description mismatch. Expected empty, Actual: [" + actualDescription + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Description mismatch. Expected empty, Actual: [" + actualDescription + "]");
                        return false;
                    }
                } else {
                    if (!actualDescription.contains(expectedDescription)) {
                        log.info("Row " + i + " Description mismatch. Expected contains: [" + expectedDescription
                                + "] Actual: [" + actualDescription + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Description mismatch. Expected contains: [" + expectedDescription
                                + "] Actual: [" + actualDescription + "]");
                        return false;
                    }
                }
                // Location A
                if (expectedLocationA.isEmpty()) {
                    if (!actualLocationA.isEmpty()) {
                        log.info("Row " + i + " Location A mismatch. Expected empty, Actual: [" + actualLocationA + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Location A mismatch. Expected empty, Actual: [" + actualLocationA + "]");
                        return false;
                    }
                } else {
                    if (!actualLocationA.contains(expectedLocationA)) {
                        log.info("Row " + i + " Location A mismatch. Expected contains: [" + expectedLocationA
                                + "] Actual: [" + actualLocationA + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Location A mismatch. Expected contains: [" + expectedLocationA
                                + "] Actual: [" + actualLocationA + "]");
                        return false;
                    }
                }
                // Location Z
                if (expectedLocationZ.isEmpty()) {
                    if (!actualLocationZ.isEmpty()) {
                        log.info("Row " + i + " Location Z mismatch. Expected empty, Actual: [" + actualLocationZ + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Location Z mismatch. Expected empty, Actual: [" + actualLocationZ + "]");
                        return false;
                    }
                } else {
                    if (!actualLocationZ.contains(expectedLocationZ)) {
                        log.info("Row " + i + " Location Z mismatch. Expected contains: [" + expectedLocationZ
                                + "] Actual: [" + actualLocationZ + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Location Z mismatch. Expected contains: [" + expectedLocationZ
                                + "] Actual: [" + actualLocationZ + "]");
                        return false;
                    }
                }
            }
            log.info("Strict table validation passed for Reference, Description, Location A and Location Z.");
            this.driver.getExecutionLogReporter().info("Strict table validation passed for Reference, Description, Location A and Location Z.");
            return true;
        } catch (Exception e) {
            log.info("Error while validating table rows in strict order.");
            this.driver.getExecutionLogReporter().error("Error while validating table rows in strict order.");
            return false;
        }
    }

}

    