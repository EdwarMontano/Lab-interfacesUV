/*
 * usartTXmodule.c
 *
 * Created: 2019-07-12 10:48:50 a.m.
 *  Author: BladimirBaccaCortes
 */ 
#include <avr/io.h>

void doUSARTinit()
{
	// Communication Parameters: 8 Data, 1 Stop, Even Parity
	// USART Receiver: Off
	// USART Transmitter: On
	// USART Mode: Asynchronous
	// USART Baud Rate: 9600
	UCSRA = 0x00;
	UCSRB = 0x18;
	UCSRC = 0x86;
	UBRRH = 0x00;
	UBRRL = 0x19;
}

int isUSARTudrEmpty()
{
	if ((UCSRA & (1 << UDRE)))
		return 1;
	else
		return 0;
}

int isUSARTrxComplete()
{
	if ((UCSRA & (1 << RXC)))
		return 1;
	else
		return 0;
}

void setUSARTudr(unsigned char dataTX)
{
	UDR = dataTX;
}

unsigned char getUSARTdata()
{
	return UDR;
}

void doTxString(unsigned char *str)
{
	int i=0;
	
	while (str[i])
	{
		while(!isUSARTudrEmpty());
		setUSARTudr(str[i]);
		i++;	
	}
}
