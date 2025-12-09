package com.simplifyqa.codeeditor;

import com.simplifyqa.abstraction.driver.IQAWebDriver;
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
            if (value1.trim().toLowerCase().equalsIgnoreCase(value2.trim().toLowerCase())) {
                log.info("value1: " + value1 + "value2 :" + value2);
                return true;
            }
            return false;
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

    
}

    