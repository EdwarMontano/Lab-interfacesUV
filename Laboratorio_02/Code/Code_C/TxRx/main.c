/*
 * TxRx.c
 *
 * Created: 13-Aug-20 16:51:16
 * Author : Admin
 */ 

#include <avr/io.h>
#include "lcd.h"

#define		F_CPU	4000000L
#define		TX_DELAY	250
#include <util/delay.h>

int main(void)
{
	unsigned char stringNo1 = "Transmitting from Proteus ... \n\r";
	unsigned char stringNo2 = "Another string from Proteus ... \n\r";
	unsigned char txAlert = "Transmitting...";
	unsigned char rxAlert = "Receiving...";
	unsigned char rxString[16];
	int iRx;
	unsigned char rxData;
	
	// Configure IO ports
	DDRA = 0x00;
	DDRD = 0x02;
	DDRB = 0xff;
	
	 // USART initialization
	 doUSARTinit();
	 
	 //Inits
	 lcd_init(LCD_DISP_ON);
	 lcd_clrscr();
	 lcd_home();
	 
    /* Replace with your application code */
    while (1) 
    {
		if (PINA & 0x01)
		{
			// TX string...
			lcd_gotoxy(0, 0);
			lcd_puts(txAlert);
			
			if (PINA & 0x02)
				// TX string No. 1
				doTxString(stringNo1);
			else
				// Tx string No. 2
				doTxString(stringNo2);
				
			_delay_ms(TX_DELAY);
		}
		else
		{
			// RX data...
			lcd_gotoxy(0, 0);
			lcd_puts(rxAlert);
			
			lcd_gotoxy(0, 1);
			while (!isUSARTrxComplete());
			rxData = getUSARTdata();
			//lcd_clrscr();
			iRx = 0;
			while(rxData)
			{
				rxString[iRx] = rxData;
				while (!isUSARTrxComplete());
				rxData = getUSARTdata();
				iRx++;
				PORTB = iRx;
				if (iRx == 15)
					break;
			}
			rxString[iRx] = 0;
			lcd_puts(rxString);
		}
    }
}

