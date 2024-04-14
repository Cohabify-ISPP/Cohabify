import { Builder, Browser, By, Key, until } from 'selenium-webdriver'
import firefox from 'selenium-webdriver/firefox.js';
import { fileURLToPath } from 'url';
import { dirname } from 'path';
import path from 'path';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.resolve(dirname(__filename),'..');
const geckoDriverPath = __dirname+'/utils/geckodriver.exe';

const openDriver = async() => {
  let serviceBuilder = new firefox.ServiceBuilder(geckoDriverPath);
  let options = new firefox.Options();
  let driver = new Builder()
      .forBrowser(Browser.FIREFOX)
      .setFirefoxService(serviceBuilder)
      .setFirefoxOptions(options)
      .build();
  return driver;
};

const login = async() => {  
  let driver = await openDriver();
  try{
    await driver.get('http://localhost:5173/login');
    await driver.findElement(By.id('username')).sendKeys('javgaragu90', Key.RETURN);
    await driver.findElement(By.id('password')).sendKeys('javgaragu90', Key.RETURN);
    await driver.findElement(By.className('btn-primary')).click();
    await driver.wait(async () => {
      const element = await driver.findElement(By.id("loginModal"));
      const displayStyle = await element.getCssValue('display');
      return displayStyle === 'block';
    }, 10000);
  }finally {
    await driver.quit();
  }
};

const socialLogin = async() => {
  let driver = await openDriver();
  try {
    await driver.get('http://localhost:5173/login');
    await driver.findElement(By.id("bypass")).click();
    
    await driver.wait(until.urlContains("/register"), 10000);
    await driver.wait(until.elementLocated(By.name('name')), 10000).getAttribute('required') !== null;
    await driver.wait(until.elementLocated(By.name('username')), 10000).getAttribute('required') !== null;
    await driver.wait(until.elementLocated(By.name('phone')), 10000).getAttribute('required') !== null;
    
    const emailInput = await driver.wait(until.elementLocated(By.name('email')), 10000);
    const emailValue = await emailInput.getAttribute('value').then(text=> text.match('.*@.*\..*'));
    const emailDisabled = await emailInput.getAttribute('readonly');

    if (!emailValue) {
      throw new Error("El correo no se ha rellenado correctamente")
    } else if (!emailDisabled) {
      throw new Error("El correo no está deshabilitado")
    }
  } catch (e) {
    throw new Error("Error en el login social: " + e);
  } finally {
    await driver.quit();
  }
};

const loginTest = async() => {
    await login();
    await socialLogin();
}

export default loginTest;