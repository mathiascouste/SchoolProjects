
/* 
 * Example of using the ChainableRGB library for controlling a Grove RGB.
 * This code cycles through all the colors in an uniform way. This is accomplished using a HSB color space. 
 */


#include <Wire.h>
#include <ChainableLED.h>

#define I2C_ADDRESS 0x12

#define MODE_RLY 0
#define MODE_LED 1
#define MODE_CUR 2

#define PIN_LED1 7
#define PIN_LED2 8
#define PIN_RLY 2
#define PIN_CUR A0

ChainableLED leds(PIN_LED1, PIN_LED2, 1);
int mode = MODE_RLY;
int mode_cpt = -1;

int data;
int inputs[3][3] = {{0,0,0},{0,0,0},{0,0,0}};
int outputs[3] = {0,0,0};
int mode_def[3] = {1,3,0};

void setup()
{
  Wire.begin(I2C_ADDRESS);
  Wire.onReceive(receiveData);
  Wire.onRequest(sendData);
  
  leds.init();
  
  pinMode(PIN_RLY, OUTPUT);
}

void loop()
{
  outputs[MODE_CUR] = analogRead(PIN_CUR);
  delay(500);
}

void executeModeAction() {
  switch(mode) {
    case MODE_RLY:
      digitalWrite(PIN_RLY, inputs[mode][0] == 1 ? HIGH : LOW);
      outputs[mode] = inputs[mode][1];
      break;
    case MODE_LED:
      leds.setColorRGB(0, inputs[mode][0],inputs[mode][1],inputs[mode][2]);
      outputs[mode] = inputs[mode][0] | inputs[mode][1] | inputs[mode][2];
      break;
  } 
}

void receiveData(int byteCount){
    while(Wire.available()) {
        data = Wire.read();
        if(mode_cpt < 0) {
          mode = data;
          mode_cpt = 0;
        } else {
          inputs[mode][mode_cpt] = data;
          mode_cpt++;
        }
        if(mode_cpt >= mode_def[mode]) {
          executeModeAction();
          mode_cpt = -1;
        }
        
    }
}

void sendData(){
    Wire.write(outputs[mode]);
}
