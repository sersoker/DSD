/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "aritmetica.h"


int 
practica1_1(char *host,char op ,int valor1,int valor2)
{
	CLIENT *clnt;
	int  *result_1;
	int sum_1_arg1;
	int sum_1_arg2;
	int  *result_2;
	int mul_1_arg1;
	int mul_1_arg2;
	int  *result_3;
	int res_1_arg1;
	int res_1_arg2;
	int  *result_4;
	int div_1_arg1;
	int div_1_arg2;

#ifndef	DEBUG

	clnt = clnt_create (host, PRACTICA1, VERSIONACTOCA1, "udp");
	if (clnt == NULL) {
		printf ("Servidor no activo\n");	
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */
   switch(op){ 
   		default:
				printf ("Operador no valido\n");	
				exit (-1);
   		break;
        case '+':
        sum_1_arg1=valor1;
        sum_1_arg2=valor2;
			result_1 = sum_1(sum_1_arg1, sum_1_arg2, clnt);
			if (result_1 == (int *) NULL) {
				clnt_perror (clnt, "call failed");
			}
        return * result_1;
        break;
       
       case 'x':
		mul_1_arg1=valor1;
		mul_1_arg2=valor2;
			result_2 = mul_1(mul_1_arg1, mul_1_arg2, clnt);
			if (result_2 == (int *) NULL) {
				clnt_perror (clnt, "call failed");
			}
		return * result_2;
		break;

		case '-':
		res_1_arg1=valor1;
		res_1_arg2=valor2;
			result_3 = res_1(res_1_arg1, res_1_arg2, clnt);
			if (result_3 == (int *) NULL) {
				clnt_perror (clnt, "call failed");
			}
		return * result_3;
		break;

		case '/':
				div_1_arg1=valor1;
				div_1_arg2=valor2;
			if(valor2!=0){	
					result_4 = div_1(div_1_arg1, div_1_arg2, clnt);
					if (result_4 == (int *) NULL) {
						clnt_perror (clnt, "call failed");
					}
			return * result_4;
			}
			else{
				printf ("Division por 0 no permitida\n");	
				exit (-1);
				}	
		break;
		
    }

#ifndef	DEBUG

	clnt_destroy (clnt);
#endif	 /* DEBUG */
}


int
main (int argc, char *argv[]){

	if (argc < 2) {
		printf ("Formato Llamada: <programa> <maquina> <entero> <operador(+ | - | x | /)> <entero>. usage: %s server_host\n", argv[0]);
		exit (1);
	}

	printf ("Resultado: %d \n",practica1_1 (argv[1],*argv[3],atoi(argv[2]),atoi(argv[4])));
exit (0);
}
