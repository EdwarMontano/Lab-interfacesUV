/*
 * usartTXmodule.h
 *
 * Created: 2019-07-12 10:48:41 a.m.
 *  Author: BladimirBaccaCortes
 */ 

void doUSARTinit();
int isUSARTudrEmpty();
int isUSARTrxComplete();
void setUSARTudr(unsigned char dataTX);
unsigned char getUSARTdata();
void doTxString(unsigned char *str);
