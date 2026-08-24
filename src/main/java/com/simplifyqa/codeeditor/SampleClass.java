package com.simplifyqa.codeeditor;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import net.bytebuddy.asm.Advice.Enter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.openqa.selenium.support.Color;
import com.simplifyqa.pluginbase.plugin.execution.IExecutionLogReporter;
import com.simplifyqa.pluginbase.common.enums.BrowserType;
import java.util.HashMap;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import java.net.URLDecoder;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.io.File;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import org.openqa.selenium.JavascriptExecutor;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;

public class SampleClass {
    @AutoInjectWebDriver
    private IQAWebDriver driver;
    @AutoInjectCurrentObject
    private SqaObject currenObject;
    private static final Logger log = Logger.getLogger(SampleClass.class.getName());
    private static final ObjectMapper JSON = new ObjectMapper();
    

    @SyncAction(uniqueId="MyProject-Sample-001", groupName="Click", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="This action belongs to WEB"))
    public boolean customSampleClick(String xpath) {
        this.driver.findElement(FindBy.xpath((String)xpath)).click();
        driver.getExecutionLogReporter().info("custom click is executed ");
        //driver.getExecutionLogReporter().info("");
        //driver.getExecutionLogReporter().error("xpath");
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-002", groupName="Type Text", description="Save to db using db url", objectTemplate=@ObjectTemplate(name=TechnologyType.ANDROID, description="This action belongs to ANDROID"))
    public boolean customSampleTypeText(String xpath, String text) {
        this.driver.findElement(FindBy.xpath((String)xpath)).enterText(text);
        driver.getExecutionLogReporter().info("enter text is executed");
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-003", groupName="Type Text", objectTemplate=@ObjectTemplate(name=TechnologyType.IOS, description="This action belongs to IOS"))
    public boolean customSampleEnterTextWithJS(String xpathOfElement, String valueToEnter) {
        this.driver.findElement(FindBy.xpath((String)xpathOfElement)).enterText(valueToEnter);
        driver.getExecutionLogReporter().info("enter text is executed");
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-004", groupName="Generic", objectTemplate=@ObjectTemplate(name=TechnologyType.GENERIC, description="This action belongs to GENERIC"))
    public boolean customAddition(String ... ints) {
        this.driver.getGenericMethods().additionOfValues(ints);
        // this.driver.getGenericMethods().additionOfValues(ints);
        driver.getExecutionLogReporter().info("addition of values performed");
        return true;
    }

    @SyncAction(uniqueId="MyProject-Sample-005", groupName="Generic", objectTemplate=@ObjectTemplate(name=TechnologyType.GENERIC, description="This action belongs to GENERIC"))
    public boolean getUnquieNumber(String suffix, IArgument value) {
        String uniqueNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSS"));
        driver.getExecutionLogReporter().info("Generated unique number: " + uniqueNumber);
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
            driver.getExecutionLogReporter().info("Generated Date: " + currentDate);
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
            driver.getExecutionLogReporter().info("value1: " + firstValue + "value2 :" + secondValue);
            return true;
          }
          else
          {
            String firstValue1 = firstValue.replace('\u00A0', ' ');
            String secondValue2 = secondValue.replace('\u00A0', ' ');
            return firstValue1.equals(secondValue2);
          }
        } catch (Exception e) {
            driver.getExecutionLogReporter().error("value1: " + value1 + "value2 :" + value2);
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
            driver.getExecutionLogReporter().info("Generated Date in GMT: " + currentDate);
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
            //Object obj = this.driver.getConfiguration().getCustomConfig().get("EXIST_TIME_OUT");
            //long timerValue = (Long) obj;
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
                driver.getExecutionLogReporter().info(v[j] + " : This List Item Is Missing in UI.");
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
                    driver.getExecutionLogReporter().info(v[i] + " : This List Item Is Missing in UI.");
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
            driver.getExecutionLogReporter().info("Stored text : " + en);
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
            driver.getExecutionLogReporter().info("Stored Attribute Value : " + en);
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
                        driver.getExecutionLogReporter().info("This : " + option + " not available in dropdown");
                        return false;
                    }
                    bStatus = true;
                }
            } else {
                driver.getExecutionLogReporter().info("Plaese check Object Attribute/Xpath");
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
                        driver.getExecutionLogReporter().info("This : " + option + " is available in dropdown");
                        return false;
                    }
                    bStatus = true;
                }
            } else {
                driver.getExecutionLogReporter().info("Plaese check Object Attribute/Xpath");
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
                    driver.getExecutionLogReporter().info("Unable to Selcet Service for this product " + Options[i] + ".");
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
            driver.getExecutionLogReporter().info("Stored Attribute Value : " + en);
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
            driver.getExecutionLogReporter().info("Unable to replace the value, The current Attribute property is " + updatedXpath);
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
                    driver.getExecutionLogReporter().info("Unable to click " + (String)updatedXpath);
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
    public boolean addMultipleValues(IArgument store, String... replacable) {
        try {
            BigDecimal total = BigDecimal.ZERO;
     
            for (String input : replacable) {
                String cleaned = input
                        .replaceAll("(USD|EUR)", "")
                        .replaceAll(",", "") // remove existing commas
                        .replaceAll("\\s+", " ")
                        .trim();
     
                if (cleaned == null || cleaned.isEmpty()) {
                    continue;
                }
     
                BigDecimal value = new BigDecimal(cleaned);
                total = total.add(value);
            }
     
            // Ensure 2 decimal places
            total = total.setScale(2, RoundingMode.HALF_UP);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String formattedValue = df.format(total);
     
            System.out.println("Formatted Value: " + formattedValue);
     
            store.updateValue(formattedValue);
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
            BigDecimal total = BigDecimal.ZERO;

            String elementXpath = this.getAttributeValue("xpath");
            int len = this.driver.findElements(FindBy.xpath((String) elementXpath)).size();

            if (len >= 1) {
                for (int i = 1; i <= len; ++i) {

                    String updatedXpath = "(" + elementXpath + ")[" + i + "]";

                    String en = this.driver.executeScript(
                            "function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}var a = getElementByXpath(\""
                                    + updatedXpath + "\");return a." + elementPro + ";",
                            new Object[0]
                    ).toString();

                    driver.getExecutionLogReporter().info("Stored Attribute Value : " + en);

                    String cleaned = en
                            .replaceAll("(USD|EUR)", "")
                            .replaceAll(",", "") // remove commas before parsing
                            .replaceAll("\\s+", " ")
                            .trim();

                    if (!cleaned.isEmpty()) {
                        BigDecimal value = new BigDecimal(cleaned);
                        total = total.add(value);
                    }
                }
            } else {
                return false;
            }

            // Round to 2 decimal places
            total = total.setScale(2, RoundingMode.HALF_UP);

            // Format to 1,112.00 style
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String formattedValue = df.format(total);

            System.out.println("Formatted Value: " + formattedValue);

            store.updateValue(formattedValue);
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

       try (PDDocument document = Loader.loadPDF(new File(pdfPath))) {

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
        driver.getExecutionLogReporter().info("Verified "+valueToBeCompared+ " is presnt in BaseString "+pageText);
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
          driver.getExecutionLogReporter().info("Generated Date in GMT: " + expectedDate);
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
            driver.getExecutionLogReporter().error("Date parsing failed. Proceeding with string comparison.");
        }

        if (firstValue.equalsIgnoreCase(secondValue)) {
            driver.getExecutionLogReporter().info("value1: " + firstValue + " value2 :" + secondValue);
            return true;
        } else {
            return firstValue.equalsIgnoreCase(secondValue);
        }

        } catch (Exception e) {
            driver.getExecutionLogReporter().error("value1: " + value1 + " value2 :" + value2);
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
                //double value = Double.parseDouble(en);
                double value = parseCurrency(en);
                double test = parseCurrency(valueToValidate);
                //BigDecimal valueBig = new BigDecimal(newValue += test).setScale(2, RoundingMode.HALF_UP);
                String expectedValue = BigDecimal.valueOf(test).toPlainString();
                BigDecimal bd = new BigDecimal(newValue += value).setScale(2, RoundingMode.HALF_UP);
                String strValue = bd.toPlainString();
                if(expectedValue.equals(strValue) || expectedValue.contains(strValue))
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
                driver.getExecutionLogReporter().info("Net Value: "+netValue+ " Tax Value: "+taxValue+" Gross Total Value: "+CalculatedGrossValue);
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
                driver.getExecutionLogReporter().info("Table xpath is empty.");
                this.driver.getExecutionLogReporter().info("Table xpath is empty.");
                return false;
            }
            if (expectedRowsInput == null || expectedRowsInput.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Expected input is empty.");
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
                    driver.getExecutionLogReporter().info("Invalid expected row format at index " + (i + 1)
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
                    driver.getExecutionLogReporter().info("Reference cannot be empty in expected row " + (i + 1));
                    this.driver.getExecutionLogReporter().info("Reference cannot be empty in expected row " + (i + 1));
                    return false;
                }
                parsedExpected.add(new String[]{reference, description, locationA, locationZ});
            }
            if (parsedExpected.isEmpty()) {
                driver.getExecutionLogReporter().info("No valid expected rows found.");
                this.driver.getExecutionLogReporter().info("No valid expected rows found.");
                return false;
            }
            // Only actual data rows (ignore footer total row)
            String rowXpath = tableXpath + "//tbody//c-design-tool-table-row";
            List<IQAWebElement> actualRows = this.driver.findElements(FindBy.xpath(rowXpath));
            if (actualRows == null || actualRows.isEmpty()) {
                driver.getExecutionLogReporter().info("No actual data rows found in table.");
                this.driver.getExecutionLogReporter().info("No actual data rows found in table.");
                return false;
            }
            // Strict order/count check
            if (actualRows.size() != parsedExpected.size()) {
                driver.getExecutionLogReporter().info("Row count mismatch. Expected: " + parsedExpected.size() + ", Actual: " + actualRows.size());
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
                    driver.getExecutionLogReporter().info("Row " + i + " Reference mismatch. Expected contains: [" + expectedReference
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
                        driver.getExecutionLogReporter().info("Row " + i + " Description mismatch. Expected empty, Actual: [" + actualDescription + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Description mismatch. Expected empty, Actual: [" + actualDescription + "]");
                        return false;
                    }
                } else {
                    if (!actualDescription.contains(expectedDescription)) {
                        driver.getExecutionLogReporter().info("Row " + i + " Description mismatch. Expected contains: [" + expectedDescription
                                + "] Actual: [" + actualDescription + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Description mismatch. Expected contains: [" + expectedDescription
                                + "] Actual: [" + actualDescription + "]");
                        return false;
                    }
                }
                // Location A
                if (expectedLocationA.isEmpty()) {
                    if (!actualLocationA.isEmpty()) {
                        driver.getExecutionLogReporter().info("Row " + i + " Location A mismatch. Expected empty, Actual: [" + actualLocationA + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Location A mismatch. Expected empty, Actual: [" + actualLocationA + "]");
                        return false;
                    }
                } else {
                    if (!actualLocationA.contains(expectedLocationA)) {
                        driver.getExecutionLogReporter().info("Row " + i + " Location A mismatch. Expected contains: [" + expectedLocationA
                                + "] Actual: [" + actualLocationA + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Location A mismatch. Expected contains: [" + expectedLocationA
                                + "] Actual: [" + actualLocationA + "]");
                        return false;
                    }
                }
                // Location Z
                if (expectedLocationZ.isEmpty()) {
                    if (!actualLocationZ.isEmpty()) {
                        driver.getExecutionLogReporter().info("Row " + i + " Location Z mismatch. Expected empty, Actual: [" + actualLocationZ + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Location Z mismatch. Expected empty, Actual: [" + actualLocationZ + "]");
                        return false;
                    }
                } else {
                    if (!actualLocationZ.contains(expectedLocationZ)) {
                        driver.getExecutionLogReporter().info("Row " + i + " Location Z mismatch. Expected contains: [" + expectedLocationZ
                                + "] Actual: [" + actualLocationZ + "]");
                        this.driver.getExecutionLogReporter().info("Row " + i + " Location Z mismatch. Expected contains: [" + expectedLocationZ
                                + "] Actual: [" + actualLocationZ + "]");
                        return false;
                    }
                }
            }
            driver.getExecutionLogReporter().info("Strict table validation passed for Reference, Description, Location A and Location Z.");
            this.driver.getExecutionLogReporter().info("Strict table validation passed for Reference, Description, Location A and Location Z.");
            return true;
        } catch (Exception e) {
            driver.getExecutionLogReporter().error("Error while validating table rows in strict order.");
            this.driver.getExecutionLogReporter().error("Error while validating table rows in strict order.");
            return false;
        }
    }

    @SyncAction(uniqueId="MyProject-Sample-048", groupName="Generic", objectTemplate=@ObjectTemplate(name=TechnologyType.GENERIC, description="This action belongs to GENERIC"))
    public boolean validateTwoDatesswithFormat(String value1, String value2) {
        try {
            LocalDate date1 = parseDateFlexible(value1);
            LocalDate date2 = parseDateFlexible(value2);

            if (date1 != null && date2 != null) {
                return date1.isEqual(date2);
            }

            // fallback (string compare)
            return value1.trim().equalsIgnoreCase(value2.trim());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

  // 🔹 Flexible parser (supports multiple formats)
    public LocalDate parseDateFlexible(String value) {
        if (value == null || value.trim().isEmpty()) return null;

        String cleaned = value.trim().replace('\u00A0', ' ');

        String[] formats = {
                "dd-MMM-yyyy",   // 07-May-2026
                "dd MMM yyyy",   // 07 May 2026
                "dd.MM.yyyy",    // 07.05.2026
                "dd/MM/yyyy",    // 07/05/2026
                "dd-MM-yyyy"     // 07-05-2026
        };

        for (String fmt : formats) {
            try {
                DateTimeFormatter formatter = fmt.contains("MMM")
                        ? DateTimeFormatter.ofPattern(fmt, Locale.ENGLISH)
                        : DateTimeFormatter.ofPattern(fmt);

                String normalized = fmt.contains("MMM")
                        ? cleaned
                        : cleaned.replace("/", ".").replace("-", ".");

                return LocalDate.parse(normalized, formatter);

            } catch (DateTimeParseException ignored) {
            }
        }

        return null;
    }

    @SyncAction(uniqueId = "MyProject-Sample-049", groupName = "Web", objectTemplate = @ObjectTemplate(name = TechnologyType.WEB, description = "Validate Gross Total Value"))
    public boolean verifyGreaterValue(String value, String expectedInput) {
        try {
            BigDecimal inputValue = new BigDecimal(value.replace(",", "").trim());
            BigDecimal expectedValue = new BigDecimal(expectedInput.replace(",", "").trim());

            return inputValue.compareTo(expectedValue) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SyncAction(
        uniqueId = "validate-column-value-all-rows",
        groupName = "Web",
        objectTemplate = @ObjectTemplate(
                name = TechnologyType.WEB,
                description = "Validate that all rows in the given column match the expected value"
        )
    )
    public boolean validateColumnValueAllRows(String columnName, String expectedValue) {
        try {
            String tableXpath = this.getAttributeValue("xpath");

            if (tableXpath == null || tableXpath.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Table xpath is empty.");
                return false;
            }

            if (columnName == null || columnName.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Column name is empty.");
                return false;
            }

            if (expectedValue == null) {
                driver.getExecutionLogReporter().info("Expected value is null.");
                return false;
            }

            String normalizedColumn = columnName.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
            String normalizedExpected = expectedValue.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();

            // All data rows in tbody (ignore header row)
            String rowXpath = tableXpath + "//tbody//tr[@data-row-number]";
            List<IQAWebElement> actualRows = this.driver.findElements(FindBy.xpath(rowXpath));

            if (actualRows == null || actualRows.isEmpty()) {
                driver.getExecutionLogReporter().info("No data rows found in table.");
                return false;
            }

            // Verify the column actually exists in the table header
            String headerXpath = tableXpath + "//thead//th[@aria-label=\"" + normalizedColumn + "\""
                    + " or .//span[@title=\"" + normalizedColumn + "\"]"
                    + " or normalize-space(.)=\"" + normalizedColumn + "\"]";
            List<IQAWebElement> headerCells = this.driver.findElements(FindBy.xpath(headerXpath));
            if (headerCells == null || headerCells.isEmpty()) {
                driver.getExecutionLogReporter().info("Column [" + normalizedColumn + "] not found in table header.");
                return false;
            }

            String cellTextScript =
                    "function getElementByXpath(path){return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}" +
                    "var el = getElementByXpath(arguments[0]);" +
                    "if(!el) return '';" +
                    // Prefer data-cell-value when present (Salesforce LWC datatable),
                    // otherwise fall back to visible text.
                    "var dv = el.getAttribute && el.getAttribute('data-cell-value');" +
                    "if (dv !== null && dv !== undefined && dv !== '') return dv;" +
                    "return (el.innerText || el.textContent || '').replace(/\\u00A0/g,' ').replace(/\\s+/g,' ').trim();";

            boolean allMatched = true;

            for (int i = 1; i <= actualRows.size(); i++) {
                // Match column cell by data-label (works for both <td> and <th> rowheader cells)
                String cellXpath = "(" + rowXpath + ")[" + i + "]//*[@data-label=\"" + normalizedColumn + "\"]";

                String actualValue = String.valueOf(this.driver.executeScript(cellTextScript, cellXpath));
                actualValue = actualValue.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();

                if (normalizedExpected.isEmpty()) {
                    if (!actualValue.isEmpty()) {
                        driver.getExecutionLogReporter().info("Row " + i + " [" + normalizedColumn + "] mismatch. Expected empty, Actual: ["
                                + actualValue + "]");
                        allMatched = false;
                    }
                } else {
                    if (!actualValue.equalsIgnoreCase(normalizedExpected)) {
                        driver.getExecutionLogReporter().info("Row " + i + " [" + normalizedColumn + "] mismatch. Expected: ["
                                + normalizedExpected + "] Actual: [" + actualValue + "]");
                        allMatched = false;
                    }
                }
            }

            if (allMatched) {
                driver.getExecutionLogReporter().info("All " + actualRows.size() + " rows have [" + normalizedColumn
                        + "] = [" + normalizedExpected + "]");
            }

            return allMatched;

        } catch (Exception e) {
            driver.getExecutionLogReporter().error("Error while validating column [" + columnName + "] for all rows.");
            return false;
        }
    }

    @SyncAction(
        uniqueId = "shadow-click",
        groupName = "Web",
        objectTemplate = @ObjectTemplate(
                name = TechnologyType.WEB,
                description = "Click an element inside (nested) shadow DOM using CSS path"
        )
)
    public boolean clickElementInShadowDom() {
        try {
            String shadowCssPath = this.getAttributeValue("css");
            if (shadowCssPath == null || shadowCssPath.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Shadow CSS path is empty.");
                return false;
            }
            String[] selectors = shadowCssPath.split(">>>");
            String script =
                    "var node = document;" +
                    "for (var i = 0; i < arguments.length; i++) {" +
                    "  if (i === 0) { node = node.querySelector(arguments[i]); }" +
                    "  else { if (!node || !node.shadowRoot) return false;" +
                    "         node = node.shadowRoot.querySelector(arguments[i]); }" +
                    "  if (!node) return false;" +
                    "}" +
                    "node.scrollIntoView({block:'center'});" +
                    "node.click();" +
                    "return true;";
            boolean clicked = Boolean.TRUE.equals(this.driver.executeScript(script, (Object[]) selectors));
            if (!clicked) {
                driver.getExecutionLogReporter().info("Failed to find/click shadow element: " + shadowCssPath);
            } else {
                driver.getExecutionLogReporter().info("Clicked shadow element: " + shadowCssPath);
            }
            return clicked;
        } catch (Exception e) {
            return false;
        }
    }

    @SyncAction(
        uniqueId = "Wait for API job to sync data",
        groupName = "Web",
        objectTemplate = @ObjectTemplate(
                name = TechnologyType.WEB,
                description = "Wait for API job to sync data"
        ),
        objectRequired = false)
    public boolean waitForAPIJobToSyncData() {
    try {
        long waitMillis = 5 * 60 * 1000L; // 3 minutes
        driver.getExecutionLogReporter().info("Pausing execution for 5 minutes...");
        Thread.sleep(waitMillis);
        driver.getExecutionLogReporter().info("Resumed after 5 minutes wait.");
        return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            driver.getExecutionLogReporter().info("Wait was interrupted.");
            return false;
        } catch (Exception e) {
            driver.getExecutionLogReporter().error("Error during wait.");
            return false;
        }
    }

    @SyncAction(
        uniqueId = "shadow-element-exists",
        groupName = "Assertions",
        objectTemplate = @ObjectTemplate(
                name = TechnologyType.WEB,
                description = "Check if an element exists inside (nested) Shadow DOM"
        )
)
    public boolean isShadowElementPresent() {
        try {
            String shadowCssPath = this.getAttributeValue("css");
            if (shadowCssPath == null || shadowCssPath.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Shadow CSS path is empty.");
                return false;
            }

            String[] selectors = shadowCssPath.split(">>>");

            String script =
                    "var node = document;" +
                    "for (var i = 0; i < arguments.length; i++) {" +
                    "  if (i === 0) { node = node.querySelector(arguments[i]); }" +
                    "  else { if (!node || !node.shadowRoot) return false;" +
                    "         node = node.shadowRoot.querySelector(arguments[i]); }" +
                    "  if (!node) return false;" +
                    "}" +
                    "return true;";

            boolean exists = Boolean.TRUE.equals(this.driver.executeScript(script, (Object[]) selectors));

            driver.getExecutionLogReporter().info("Shadow element [" + shadowCssPath + "] exists = " + exists);
            return exists;

        } catch (Exception e) {
            driver.getExecutionLogReporter().error("Error checking shadow DOM element existence.");
            return false;
        }
    }

    @SyncAction(
        uniqueId = "shadow-type-text",
        groupName = "Web",
        objectTemplate = @ObjectTemplate(
                name = TechnologyType.WEB,
                description = "Type text into a Shadow DOM input/textarea"
        )
    )
    public boolean typeTextInShadowDom(String valueToType) {
        try {
            String shadowCssPath = this.getAttributeValue("css");
            if (shadowCssPath == null || shadowCssPath.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Shadow CSS path is empty.");
                return false;
            }
            if (valueToType == null) {
                valueToType = "";
            }
            String[] selectors = shadowCssPath.split(">>>");
            String script =
                    "var value = arguments[arguments.length - 1];" +
                    "var node = document;" +
                    "for (var i = 0; i < arguments.length - 1; i++) {" +
                    "  if (i === 0) { node = node.querySelector(arguments[i]); }" +
                    "  else { if (!node || !node.shadowRoot) return false;" +
                    "         node = node.shadowRoot.querySelector(arguments[i]); }" +
                    "  if (!node) return false;" +
                    "}" +
                    "node.scrollIntoView({block:'center'});" +
                    "node.focus();" +
                    "var proto = node.tagName === 'TEXTAREA' ? window.HTMLTextAreaElement.prototype : window.HTMLInputElement.prototype;" +
                    "var setter = Object.getOwnPropertyDescriptor(proto, 'value').set;" +
                    "setter.call(node, value);" +
                    "node.dispatchEvent(new Event('input', { bubbles: true }));" +
                    "node.dispatchEvent(new Event('change', { bubbles: true }));" +
                    "return true;";
            Object[] args = new Object[selectors.length + 1];
            System.arraycopy(selectors, 0, args, 0, selectors.length);
            args[selectors.length] = valueToType;
            boolean typed = Boolean.TRUE.equals(this.driver.executeScript(script, args));
            if (!typed) {
                driver.getExecutionLogReporter().info("Failed to type in shadow input: " + shadowCssPath);
            } else {
                driver.getExecutionLogReporter().info("Typed [" + valueToType + "] into shadow input: " + shadowCssPath);
            }
            return typed;
        } catch (Exception e) {
            driver.getExecutionLogReporter().error("Error typing into shadow DOM input.");
            return false;
        }
    }

    @SyncAction(
        uniqueId = "shadow-select-from-combobox",
        groupName = "Web",
        objectTemplate = @ObjectTemplate(
                name = TechnologyType.WEB,
                description = "Select a value from a Headless UI combobox/dropdown inside Shadow DOM"
        )
    )
    public boolean selectFromShadowCombobox(String valueToSelect) {
        try {
            String shadowCssPath = this.getAttributeValue("css");
            if (shadowCssPath == null || shadowCssPath.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Shadow CSS path is empty.");
                return false;
            }
            if (valueToSelect == null || valueToSelect.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Value to select is empty.");
                return false;
            }
            String[] selectors = shadowCssPath.split(">>>");
            // Walk to input -> open -> clear -> type
            String openAndTypeScript =
                    "var value = arguments[arguments.length - 1];" +
                    "var node = document;" +
                    "for (var i = 0; i < arguments.length - 1; i++) {" +
                    "  if (i === 0) { node = node.querySelector(arguments[i]); }" +
                    "  else { if (!node || !node.shadowRoot) return 'INPUT_NOT_FOUND';" +
                    "         node = node.shadowRoot.querySelector(arguments[i]); }" +
                    "  if (!node) return 'INPUT_NOT_FOUND';" +
                    "}" +
                    "var input = node;" +
                    "input.scrollIntoView({block:'center'});" +
                    "input.focus();" +
                    "input.click();" +
                    "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype,'value').set;" +
                    "setter.call(input, '');" +
                    "input.dispatchEvent(new Event('input',{bubbles:true}));" +
                    "setter.call(input, value);" +
                    "input.dispatchEvent(new Event('input',{bubbles:true}));" +
                    "input.dispatchEvent(new Event('change',{bubbles:true}));" +
                    "return 'OK';";
            Object[] args = new Object[selectors.length + 1];
            System.arraycopy(selectors, 0, args, 0, selectors.length);
            args[selectors.length] = valueToSelect;
            if (!"OK".equals(String.valueOf(this.driver.executeScript(openAndTypeScript, args)))) {
                driver.getExecutionLogReporter().info("Combobox input not found: " + shadowCssPath);
                return false;
            }
            // Click matching option using pointer events (Headless UI listens to these)
            String clickOptionScript =
                    "var value = arguments[arguments.length - 1];" +
                    "var node = document;" +
                    "for (var i = 0; i < arguments.length - 1; i++) {" +
                    "  if (i === 0) { node = node.querySelector(arguments[i]); }" +
                    "  else { if (!node || !node.shadowRoot) return 'NO_INPUT';" +
                    "         node = node.shadowRoot.querySelector(arguments[i]); }" +
                    "  if (!node) return 'NO_INPUT';" +
                    "}" +
                    "var input = node;" +
                    "var root = input.getRootNode();" +
                    "var options = root.querySelectorAll(\"[role='option']\");" +
                    "if (!options || options.length === 0) return 'NO_OPTIONS';" +
                    "var target = null;" +
                    "for (var j = 0; j < options.length; j++) {" +
                    "  var txt = (options[j].innerText || options[j].textContent || '').replace(/\\u00A0/g,' ').replace(/\\s+/g,' ').trim();" +
                    "  if (txt.toLowerCase() === value.toLowerCase()) { target = options[j]; break; }" +
                    "}" +
                    "if (!target) {" +
                    "  for (var k = 0; k < options.length; k++) {" +
                    "    var txt2 = (options[k].innerText || options[k].textContent || '').replace(/\\u00A0/g,' ').replace(/\\s+/g,' ').trim();" +
                    "    if (txt2.toLowerCase().indexOf(value.toLowerCase()) !== -1) { target = options[k]; break; }" +
                    "  }" +
                    "}" +
                    "if (!target) return 'NOT_FOUND';" +
                    "target.scrollIntoView({block:'center'});" +
                    // Headless UI uses pointer events — dispatch the full sequence.
                    "var rect = target.getBoundingClientRect();" +
                    "var x = rect.left + rect.width/2;" +
                    "var y = rect.top + rect.height/2;" +
                    "var opts = {bubbles:true, cancelable:true, composed:true, clientX:x, clientY:y, button:0, buttons:1};" +
                    "target.dispatchEvent(new PointerEvent('pointerdown', opts));" +
                    "target.dispatchEvent(new MouseEvent('mousedown', opts));" +
                    "target.dispatchEvent(new PointerEvent('pointerup', opts));" +
                    "target.dispatchEvent(new MouseEvent('mouseup', opts));" +
                    "target.dispatchEvent(new MouseEvent('click', opts));" +
                    "return 'OK';";
            boolean clicked = false;
            String lastResult = "";
            for (int attempt = 0; attempt < 10; attempt++) {
                Object res = this.driver.executeScript(clickOptionScript, args);
                lastResult = String.valueOf(res);
                if ("OK".equals(lastResult)) {
                    clicked = true;
                    break;
                }
                Thread.sleep(500);
            }
            // Fallback — if pointer events didn't take, press Enter on the input
            if (!clicked) {
                driver.getExecutionLogReporter().info("Pointer click on option failed (" + lastResult + "). Trying Enter key fallback.");
                String enterFallbackScript =
                        "var node = document;" +
                        "for (var i = 0; i < arguments.length; i++) {" +
                        "  if (i === 0) { node = node.querySelector(arguments[i]); }" +
                        "  else { if (!node || !node.shadowRoot) return false;" +
                        "         node = node.shadowRoot.querySelector(arguments[i]); }" +
                        "  if (!node) return false;" +
                        "}" +
                        "var input = node;" +
                        "input.focus();" +
                        "var down = new KeyboardEvent('keydown', {key:'Enter', code:'Enter', keyCode:13, which:13, bubbles:true, cancelable:true, composed:true});" +
                        "var up = new KeyboardEvent('keyup', {key:'Enter', code:'Enter', keyCode:13, which:13, bubbles:true, cancelable:true, composed:true});" +
                        "input.dispatchEvent(down);" +
                        "input.dispatchEvent(up);" +
                        "return true;";
                clicked = Boolean.TRUE.equals(this.driver.executeScript(enterFallbackScript, (Object[]) selectors));
            }
            if (!clicked) {
                driver.getExecutionLogReporter().info("Could not select [" + valueToSelect + "] in combobox: " + shadowCssPath);
                return false;
            }
            driver.getExecutionLogReporter().info("Selected [" + valueToSelect + "] in combobox: " + shadowCssPath);
            return true;
        } catch (Exception e) {
            driver.getExecutionLogReporter().error("Error selecting from shadow combobox.");
            return false;
        }
    }

    @SyncAction(
        uniqueId = "shadow-click-collapsible-by-heading",
        groupName = "Web",
        objectTemplate = @ObjectTemplate(
                name = TechnologyType.WEB,
                description = "Click a sidebar collapsible by its heading text (Shadow DOM aware)"
        ),
        objectRequired = false
    )
    public boolean clickSidebarCollapsibleByHeading(String headingText) {
        try {
            if (headingText == null || headingText.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Heading text is empty.");
                return false;
            }

            String script =
                    "var heading = arguments[0].toLowerCase();" +
                    "var sidebar = document.querySelector('pag-sidebar document-info');" +
                    "if (!sidebar || !sidebar.shadowRoot) return 'NO_SIDEBAR';" +
                    "var collapsibles = sidebar.shadowRoot.querySelectorAll('pag-collapsible');" +
                    "for (var i = 0; i < collapsibles.length; i++) {" +
                    "  var c = collapsibles[i];" +
                    "  if (!c.shadowRoot) continue;" +
                    "  var btn = c.shadowRoot.querySelector('[role=\"button\"]');" +
                    "  if (!btn) continue;" +
                    "  var txt = (btn.textContent || '').replace(/\\u00A0/g,' ').replace(/\\s+/g,' ').trim().toLowerCase();" +
                    "  if (txt.indexOf(heading) !== -1) {" +
                    "    btn.scrollIntoView({block:'center'});" +
                    "    var rect = btn.getBoundingClientRect();" +
                    "    var o = {bubbles:true, cancelable:true, composed:true, clientX:rect.left+rect.width/2, clientY:rect.top+rect.height/2, button:0, buttons:1};" +
                    "    btn.dispatchEvent(new PointerEvent('pointerdown', o));" +
                    "    btn.dispatchEvent(new MouseEvent('mousedown', o));" +
                    "    btn.dispatchEvent(new PointerEvent('pointerup', o));" +
                    "    btn.dispatchEvent(new MouseEvent('mouseup', o));" +
                    "    btn.dispatchEvent(new MouseEvent('click', o));" +
                    "    return 'OK';" +
                    "  }" +
                    "}" +
                    "return 'NOT_FOUND';";

            String result = String.valueOf(this.driver.executeScript(script, headingText));

            if ("OK".equals(result)) {
                driver.getExecutionLogReporter().info("Clicked sidebar collapsible: " + headingText);
                return true;
            }

            driver.getExecutionLogReporter().info("Failed to click collapsible [" + headingText + "]. Reason: " + result);
            return false;

        } catch (Exception e) {
            driver.getExecutionLogReporter().error("Error clicking sidebar collapsible by heading.");
            return false;
        }
    }   
    @SyncAction(
        uniqueId = "validate-xml-node-value",
        groupName = "Assertions",
        objectTemplate = @ObjectTemplate(
                name = TechnologyType.WEB,
                description = "Validate that an XML node has the expected value (namespace-agnostic, flexible match)"
        ),
        objectRequired = false
    )
    public boolean validateXmlNodeValue(String filePath, String nodeName, String expectedValue) {
        try {
            if (filePath == null || filePath.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("File path is empty.");
                return false;
            }
            if (nodeName == null || nodeName.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Node name is empty.");
                return false;
            }
            if (expectedValue == null) {
                driver.getExecutionLogReporter().info("Expected value is null.");
                return false;
            }

            java.io.File xmlFile = new java.io.File(filePath);
            if (!xmlFile.exists() || !xmlFile.isFile()) {
                driver.getExecutionLogReporter().info("XML file not found: " + filePath);
                return false;
            }

            // Parse XML (namespace-unaware so we can match by local name regardless of cbc:/cac: prefix)
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(xmlFile);

            // Find every element whose local name matches (ignore namespace prefix)
            org.w3c.dom.NodeList all = doc.getElementsByTagName("*");
            java.util.List<org.w3c.dom.Element> matches = new java.util.ArrayList<>();
            for (int i = 0; i < all.getLength(); i++) {
                org.w3c.dom.Element el = (org.w3c.dom.Element) all.item(i);
                String tag = el.getTagName();
                String localName = tag.contains(":") ? tag.substring(tag.indexOf(':') + 1) : tag;
                if (localName.equalsIgnoreCase(nodeName.trim())) {
                    matches.add(el);
                }
            }

            if (matches.isEmpty()) {
                driver.getExecutionLogReporter().info("No node found with name [" + nodeName + "] in XML.");
                return false;
            }

            // Try every match — return true if any of them validates the expected value
            for (org.w3c.dom.Element el : matches) {
                String fullText = el.getTextContent() == null ? "" : el.getTextContent().trim();
                String directText = getDirectTextOnly(el).trim();

                String currency = el.getAttribute("currencyID");
                String actualWithCurrency = (currency != null && !currency.isEmpty())
                        ? (currency + " " + directText).trim()
                        : directText;

                if (valuesMatch(directText, expectedValue)
                        || valuesMatch(fullText, expectedValue)
                        || valuesMatch(actualWithCurrency, expectedValue)) {
                    String shown = directText.isEmpty() ? fullText : actualWithCurrency;
                    driver.getExecutionLogReporter().info("Validated [" + nodeName + "] = [" + expectedValue + "] (actual: " + shown + ")");
                    return true;
                }
            }

            org.w3c.dom.Element first = matches.get(0);
            String firstText = (first.getTextContent() == null ? "" : first.getTextContent()).trim()
                    .replaceAll("\\s+", " ");
                    driver.getExecutionLogReporter().info("Validation failed for [" + nodeName + "]. Expected: [" + expectedValue
                    + "], Actual (first match): [" + firstText + "]");
            return false;

        } catch (Exception e) {
            driver.getExecutionLogReporter().error("Error validating XML node value.");
            return false;
        }
    }

    private String getDirectTextOnly(org.w3c.dom.Element el) {
        StringBuilder sb = new StringBuilder();
        org.w3c.dom.NodeList children = el.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            org.w3c.dom.Node n = children.item(i);
            if (n.getNodeType() == org.w3c.dom.Node.TEXT_NODE) {
                sb.append(n.getNodeValue());
            }
        }
        return sb.toString();
    }

    private boolean valuesMatch(String actual, String expected) {
        if (actual == null) actual = "";
        if (expected == null) expected = "";

        String a = actual.trim();
        String e = expected.trim();

        if (a.equalsIgnoreCase(e)) return true;

        String aNorm = a.replaceAll("\\s+", " ").toLowerCase();
        String eNorm = e.replaceAll("\\s+", " ").toLowerCase();
        if (aNorm.equals(eNorm)) return true;
        if (aNorm.contains(eNorm) || eNorm.contains(aNorm)) return true;

        Double aNum = extractFirstNumber(a);
        Double eNum = extractFirstNumber(e);
        if (aNum != null && eNum != null && Math.abs(aNum - eNum) < 0.005) {
            return true;
        }

        return false;
    }

    private Double extractFirstNumber(String s) {
        if (s == null) return null;
        String cleaned = s.replace(',', '.');
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("[-+]?\\d*\\.?\\d+").matcher(cleaned);
        if (m.find()) {
            try {
                return Double.parseDouble(m.group());
            } catch (NumberFormatException ignored) { }
        }
        return null;
    }


        @SyncAction(
            uniqueId = "validate-negative-currency-value",
            groupName = "Assertions",
            objectTemplate = @ObjectTemplate(
                    name = TechnologyType.WEB,
                    description = "Validate application currency value is negative of the expected input value"
            )
        )
        public boolean validateNegativeCurrencyValue(String expectedPositiveValue) {
            try {
                if (expectedPositiveValue == null || expectedPositiveValue.trim().isEmpty()) {
                    driver.getExecutionLogReporter().info("Expected input value is empty.");
                    return false;
                }

                String elementXpath = this.getAttributeValue("xpath");
                if (elementXpath == null || elementXpath.trim().isEmpty()) {
                    driver.getExecutionLogReporter().info("Object xpath is empty.");
                    return false;
                }

                String script =
                        "function getElementByXpath(path){return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}" +
                        "var el = getElementByXpath(arguments[0]);" +
                        "if(!el) return '';" +
                        "var dv = el.getAttribute && el.getAttribute('data-cell-value');" +
                        "if (dv !== null && dv !== undefined && dv !== '') return dv;" +
                        "return (el.innerText || el.textContent || '').replace(/\\u00A0/g,' ').replace(/\\s+/g,' ').trim();";

                Object result = this.driver.executeScript(script, elementXpath);
                if (result == null) {
                    driver.getExecutionLogReporter().info("Element not found for xpath: " + elementXpath);
                    return false;
                }

                String actualText = result.toString().replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
                if (actualText.isEmpty()) {
                    driver.getExecutionLogReporter().info("Actual value from application is empty.");
                    return false;
                }

                Double expectedAmount = extractAmount(expectedPositiveValue);
                Double actualAmount = extractAmount(actualText);

                if (expectedAmount == null) {
                    driver.getExecutionLogReporter().info("Could not parse expected value: [" + expectedPositiveValue + "]");
                    return false;
                }

                if (actualAmount == null) {
                    driver.getExecutionLogReporter().info("Could not parse actual value: [" + actualText + "]");
                    return false;
                }

                // Application value should be negative of input value
                double expectedNegativeAmount = -Math.abs(expectedAmount);
                boolean isMatch = Double.compare(actualAmount, expectedNegativeAmount) == 0;

                if (isMatch) {
                    driver.getExecutionLogReporter().info("Validation passed. Expected negative value: [" + expectedNegativeAmount
                            + "], Actual: [" + actualAmount + "], Actual text: [" + actualText + "]");
                    return true;
                }

                driver.getExecutionLogReporter().info("Validation failed. Input: [" + expectedPositiveValue
                        + "], Expected app value: [" + expectedNegativeAmount
                        + "], Actual app value: [" + actualAmount
                        + "], Actual text: [" + actualText + "]");
                return false;

            } catch (Exception e) {
                driver.getExecutionLogReporter().error("Error while validating negative currency value.", e);
                return false;
            }
        }

    private Double extractAmount(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }

        String normalized = text.trim()
                .replace('\u00A0', ' ')
                .replaceAll("[A-Za-z]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        // Handle European format like 1.500,00
        if (normalized.matches("-?\\d{1,3}(\\.\\d{3})*,\\d+")) {
            normalized = normalized.replace(".", "").replace(",", ".");
        } else {
            normalized = normalized.replace(",", "");
        }

        java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("-?\\d+(?:\\.\\d+)?")
                .matcher(normalized);

        if (matcher.find()) {
            try {
                return Double.parseDouble(matcher.group());
            } catch (NumberFormatException e) {
                return null;
            }
        }

        return null;
    }
    
    @SyncAction(
    uniqueId = "Validate-text-from-Print-Preview-PDF",
    groupName = "Assertions",
    objectTemplate = @ObjectTemplate(
            name = TechnologyType.WEB,
            description = "Validate text from Salesforce PDF Print Preview"),
    objectRequired = false
    )
    // public boolean readPdfFromCurrentBrowser(String pdfUrl, String pageNumber, String valueToBeCompared) throws Exception {

    //     String url = (pdfUrl != null) ? pdfUrl : driver.getCurrentUrl();
     
    //     int page;
    
    //     try {
    
    //         page = Integer.parseInt(pageNumber.trim());
    
    //     } catch (NumberFormatException e) {
    
    //         throw new IllegalArgumentException(
    
    //             "Invalid page number: '" + pageNumber + "'. Must be a whole number.");
    
    //     }
     
    //     String script =
    
    //         "const cb = arguments[arguments.length - 1];" +
    
    //         "fetch(arguments[0]).then(r => r.blob()).then(b => {" +
    
    //         "    const fr = new FileReader();" +
    
    //         "    fr.onload = () => cb(fr.result.substring(fr.result.indexOf(',') + 1));" +
    
    //         "    fr.readAsDataURL(b);" +
    
    //         "}).catch(e => cb('ERROR:' + e));";
     
    //     Object result =  driver.executeAsyncScript(script, url);
    
    //     String b64 = String.valueOf(result);
     
    //     if (b64.startsWith("ERROR:")) {
    
    //         throw new RuntimeException("Failed to fetch PDF: " + b64);
    
    //     }
     
    //     byte[] pdfBytes = Base64.getDecoder().decode(b64);
    
    //     try (PDDocument doc = Loader.loadPDF(pdfBytes)) {
    
    //         if (page < 1 || page > doc.getNumberOfPages()) {
    
    //             throw new IllegalArgumentException(
    
    //                 "Page " + page + " out of range. PDF has "
    
    //                 + doc.getNumberOfPages() + " pages.");
    
    //         }
     
    //         PDFTextStripper stripper = new PDFTextStripper();
    
    //         stripper.setStartPage(page);   // read only this page
    
    //         stripper.setEndPage(page);
    
    //         String pageText = stripper.getText(doc);
     
    //         return pageText.contains(valueToBeCompared);
            
    //     } catch (Exception e) {
    //         return false;
    //         // driver.getExecutionLogReporter(e.getMessage).info("Actual value from application is empty.");
    //         // TODO: handle exception
    //     }
    
    // }
    public boolean readPdfFromCurrentBrowser(String pageNumber, String valueToBeCompared) throws Exception {
        int page = Integer.parseInt(pageNumber.trim());
 
        // Resolve the REAL pdf url: prefer the embed's original-url, not the chrome-extension:// src
        String url = null;
        if (url == null || url.isEmpty()) {
            url = (String) driver.executeScript(
                "const e = document.querySelector('embed[type=\"application/x-google-chrome-pdf\"]')" +
                " || document.querySelector('embed[type=\"application/pdf\"]')" +
                " || document.querySelector('embed');" +
                "if (!e) return window.location.href;" +
                "return e.getAttribute('original-url') || e.getAttribute('src');");
        }
        System.out.println("Resolved PDF url: " + url);
     
        if (url != null && url.startsWith("chrome-extension://")) {
            throw new RuntimeException(
                "Got a chrome-extension:// URL — this can't be fetched from page JS. " +
                "The embed's original-url wasn't found. Check the embed element.");
        }
     
        String script =
            "const cb = arguments[arguments.length - 1];" +
            "fetch(arguments[0], {credentials: 'include'}).then(r => {" +
            "    if (!r.ok) { cb('ERROR:HTTP ' + r.status); return; }" +
            "    const ct = r.headers.get('content-type') || '';" +
            "    return r.blob().then(b => ({b, ct}));" +
            "}).then(o => {" +
            "    if (!o) return;" +
            "    const fr = new FileReader();" +
            "    fr.onload = () => cb(o.ct + '||' + fr.result.substring(fr.result.indexOf(',') + 1));" +
            "    fr.readAsDataURL(o.b);" +
            "}).catch(e => cb('ERROR:' + e));";
     
        Object result =  driver.executeAsyncScript(script, url);
        String raw = String.valueOf(result);
     
        if (raw.startsWith("ERROR:")) {
            throw new RuntimeException("Fetch failed: " + raw);
        }
     
        // split off the content-type we prefixed
        String contentType = raw.substring(0, raw.indexOf("||"));
        String b64 = raw.substring(raw.indexOf("||") + 2);
        System.out.println("Content-Type: " + contentType);
     
        byte[] pdfBytes = Base64.getDecoder().decode(b64);
        System.out.println("bytes length: " + pdfBytes.length);
        System.out.println("header: " + new String(pdfBytes, 0, Math.min(8, pdfBytes.length)));
     
        if (pdfBytes.length < 5 || !new String(pdfBytes, 0, 5).equals("%PDF-")) {
            throw new RuntimeException("Fetched content is NOT a PDF (likely a login/HTML page). " +
                "Content-Type=" + contentType + ", header=" +
                new String(pdfBytes, 0, Math.min(80, pdfBytes.length)));
        }
     
        try (PDDocument doc = Loader.loadPDF(pdfBytes)) {   // Loader.loadPDF(pdfBytes) for 3.x
            if (page < 1 || page > doc.getNumberOfPages()) {
                throw new IllegalArgumentException("Page " + page + " out of range. PDF has "
                    + doc.getNumberOfPages() + " pages.");
            }
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(page);
            stripper.setEndPage(page);
            return stripper.getText(doc).contains(valueToBeCompared);
        }
    }

    @SyncAction(
        uniqueId = "getAuthenticatedUrlAndStore",
        groupName = "Web",
        objectTemplate = @ObjectTemplate(
                name = TechnologyType.WEB,
                description = "Get Authenticated URL and Store"
        ),
        objectRequired = false
    )
    public boolean getAuthenticatedUrlAndStore(String passkey,IArgument defaultUrl) {
        try{
            String url = getSalesforceLoginUrl(passkey,defaultUrl.getValue());
            defaultUrl.updateValue(url);
            return true;
        }catch(Exception e){
            logReporter.error(e.getMessage());
        }
        return false;
       
    }  
    
 
    /**

     * Returns the URL the test should launch.

     *

     * @param passKey    the sfdxAuthUrl secret (force://PlatformCLI::...@instance) — from masked testdata

     * @param defaultUrl the org URL to fall back to when no passKey is given (plain launch → login page)

     * @return a freshly minted one-time frontdoor login URL (logged-in launch), or defaultUrl if passKey is blank

     */

    public static String getSalesforceLoginUrl(String passKey, String defaultUrl) throws Exception {

        if (passKey == null || passKey.isBlank()) {

            throw new RuntimeException("Passkey is empty");                                   // no secret → plain launch

        }

        passKey = passKey.trim();

        if (!passKey.startsWith("force://")) {

            throw new RuntimeException("Salesforce passKey must be the sfdxAuthUrl value (starts with force://...)");

        }
 
        // Alias derived from the org instance inside the passKey — QA/TEST/UAT keys

        // each get their own auth entry automatically, no extra parameter needed.

        String instance = passKey.substring(passKey.lastIndexOf('@') + 1);

        String alias = "sqa-" + instance.replaceAll("[^a-zA-Z0-9]", "-");
 
        // (1) once per machine/container: import the auth if this box doesn't know the org yet

        if (run(sf("org", "display", "-o", alias)).exit != 0) {

            Path tmp = Files.createTempFile("sfauth", ".txt");

            try {

                Files.writeString(tmp, passKey, StandardCharsets.UTF_8);

                CmdResult login = run(sf("org", "login", "sfdx-url", "-f", tmp.toString(), "-a", alias));

                if (login.exit != 0) {

                    throw new RuntimeException("Salesforce auth import failed (sf org login sfdx-url): " + login.output);

                }

            } finally {

                Files.deleteIfExists(tmp);                       // never leave the secret on disk

            }

        }
 
        // (2) every browser launch: mint a fresh one-time login link

        CmdResult mint = run(sf("org", "open", "-o", alias, "--url-only", "--json"));

        if (mint.exit != 0) {

            throw new RuntimeException("Salesforce login-URL minting failed (sf org open): " + mint.output);

        }

        String url = JSON.readTree(mint.output).path("result").path("url").asText();

        if (url == null || url.isBlank()) {

            throw new RuntimeException("Could not parse result.url from sf output");

        }

        return url;                                              // NOTE: a live login credential — do not log it

    }
 
    // ---------- helpers ----------
 
    /** Builds the sf command line: SF_CLI_PATH env wins; Windows goes through cmd /c (npm ships sf.cmd). */

   
    private static String[] sf(String... args) {
        boolean windows = System.getProperty("os.name").toLowerCase().contains("win");
        String sfBinary = System.getenv("SF_CLI_PATH");
        List<String> cmd = new ArrayList<>();
        if (windows) {
            cmd.add("cmd");
            cmd.add("/c");
            cmd.add(sfBinary != null && !sfBinary.isBlank() ? sfBinary : "sf");
        } else {
            if (sfBinary == null || sfBinary.isBlank()) {
                sfBinary = new File("/usr/local/bin/sf").canExecute() ? "/usr/local/bin/sf" : "sf";
            }
            cmd.add(sfBinary);
        }
        cmd.addAll(List.of(args));
        return cmd.toArray(new String[0]);
    }

    private record CmdResult(int exit, String output) {
    }

    private static CmdResult run(String[] command) throws Exception {
        ProcessBuilder pb=new ProcessBuilder(command);
        pb.environment().put("SF_AUTOUPDATE_DISABLE", "true");
        pb.environment().put("SF_SKIP_NEW_VERSION_CHECK", "true");
        Process process = pb.redirectErrorStream(true).start();
        
        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        if (!process.waitFor(120, TimeUnit.SECONDS)) {
            process.destroyForcibly();
            return new CmdResult(-1, "sf command timed out: " + String.join(" ", command));
        }
        return new CmdResult(process.exitValue(), output);
    }

 
    @SyncAction(uniqueId="EnterTextAndPressEnter", groupName="Web", description="Enter text into an input field and then press the ENTER key", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="This action belongs to WEB"))
    public boolean EnterTextAndPressEnter(String text) {
        try {
            String elementXpath = this.getAttributeValue("xpath");
            this.driver.findElement(FindBy.xpath((String)elementXpath)).enterText(text);
            driver.getExecutionLogReporter().info("Entered text: " + text);
            this.driver.keyboardAction("ENTER");
            driver.getExecutionLogReporter().info("ENTER key action performed");
            return true;
        } catch (Exception e) {
            driver.getExecutionLogReporter().error("Failed to enter text and press ENTER: " + e.toString());
            return false;
        }
    }

    @SyncAction(uniqueId="NavigateToUrl", groupName="Web", description="Launch a specified URL in the current tab", objectTemplate=@ObjectTemplate(name=TechnologyType.WEB, description="This action belongs to WEB"), objectRequired=false)
    public boolean navigateToUrl(IArgument url) {
        try {
            this.driver.launchApplication(url.getValue());
            driver.getExecutionLogReporter().info("Navigated to URL in current tab: " + url);
            return true;
        } catch (Exception e) {
            driver.getExecutionLogReporter().error("Failed to navigate to URL: " + url + " - " + e.toString());
            return false;
        }
    }

    @SyncAction(
        uniqueId = "transfer-file-sftp",
        groupName = "File Operations",
        objectTemplate = @ObjectTemplate(
                name = TechnologyType.WEB,
                description = "Connect to an SFTP server and transfer a file"
        )
)
    public boolean transferFileSFTP(
            String host,
            String port,
            String username,
            String password,
            String localFile,
            String remoteFile) {

        SSHClient ssh = new SSHClient();

        try {
            if (host == null || host.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("SFTP host is empty.");
                return false;
            }

            if (port == null || port.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("SFTP port is empty.");
                return false;
            }

            if (username == null || username.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("SFTP username is empty.");
                return false;
            }

            if (password == null || password.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("SFTP password is empty.");
                return false;
            }

            if (localFile == null || localFile.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Local file path is empty.");
                return false;
            }

            if (remoteFile == null || remoteFile.trim().isEmpty()) {
                driver.getExecutionLogReporter().info("Remote file path is empty.");
                return false;
            }

            java.io.File file = new java.io.File(localFile);

            if (!file.exists()) {
                driver.getExecutionLogReporter().info(
                        "Local file does not exist: [" + localFile + "]");
                return false;
            }

            if (!file.isFile()) {
                driver.getExecutionLogReporter().info(
                        "Local path is not a file: [" + localFile + "]");
                return false;
            }

            int sftpPort;

            try {
                sftpPort = Integer.parseInt(port.trim());
            } catch (NumberFormatException e) {
                driver.getExecutionLogReporter().info(
                        "Invalid SFTP port: [" + port + "]");
                return false;
            }

            driver.getExecutionLogReporter().info(
                    "Connecting to SFTP server: [" + host + ":" + sftpPort + "]");

            /*
            * For production, use proper host-key verification instead of
            * accepting all host keys.
            */
            ssh.addHostKeyVerifier((hostname, p, key) -> true);

            ssh.connect(host, sftpPort);

            driver.getExecutionLogReporter().info(
                    "Connected to SFTP server: [" + host + ":" + sftpPort + "]");

            ssh.authPassword(username, password);

            driver.getExecutionLogReporter().info(
                    "SFTP authentication successful for user: [" + username + "]");

            try (SFTPClient sftp = ssh.newSFTPClient()) {

                driver.getExecutionLogReporter().info(
                        "Transferring file. Local: [" + localFile
                                + "], Remote: [" + remoteFile + "]");

                sftp.put(localFile, remoteFile);

                driver.getExecutionLogReporter().info(
                        "File transferred successfully. Local: [" + localFile
                                + "], Remote: [" + remoteFile + "]");

                return true;
            }

        } catch (Exception e) {

            driver.getExecutionLogReporter().error(
                    "Error while transferring file through SFTP.", e);

            return false;

        } finally {

            try {
                if (ssh.isConnected()) {
                    ssh.disconnect();

                    driver.getExecutionLogReporter().info(
                            "SFTP connection closed.");
                }
            } catch (Exception e) {
                driver.getExecutionLogReporter().error(
                        "Error while closing SFTP connection.", e);
            }
        }
    }

}

    