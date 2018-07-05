/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "asociaciones.h"

void asociacion_1(char *host){
	CLIENT *clnt;
	existe  *result_1;
	int argumentoID;
	char *ponerasociacion_1_arg2;
	char *ponerasociacion_1_arg3;
	leer  *result_2;
	char *obtenerasociacion_1_arg2;
	existe  *result_3;
	char *borrarasociacion_1_arg2;
	resultado  *result_4;
	char clave[250];
    char valor[250];

#ifndef	DEBUG
	clnt = clnt_create (host, ASOCIACION, ASOCIACION_VER, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */
 int opcion=0;
    while(opcion!=-1){
        printf("1 - Poner Asociacion\n");
        printf("2 - Obtener Asociacion\n");
        printf("3 - Borrar Asociacion\n");
        printf("4 - Enumerar\n");
        printf("-1 - Salir\n");
    scanf("%d",&opcion);

        switch(opcion){
        	case 1:
			    printf("ID:\n");
			    scanf("%d",&argumentoID);
			    printf("Clave:\n");
			    scanf("%s",clave);
			    printf("Valor:\n");
			    scanf("%s",valor);
			    printf("ASOCIACION NUEVA: ID: %d CLAVE: %s VALOR: %s.\n",argumentoID,clave,valor);
			 
				result_1 = ponerasociacion_1(argumentoID, clave, valor, clnt);
				if (result_1 == (existe *) NULL) {
					clnt_perror (clnt, "call failed");
				}
			break;

			case 2:
			  	printf("ID:\n");
			    scanf("%d",&argumentoID);
			    printf("Clave:\n");
			    scanf("%s",&obtenerasociacion_1_arg2);
			    printf("ASOCIACION A CONSULTAR: ID: %d CLAVE: %s .\n",argumentoID,&obtenerasociacion_1_arg2);

				result_2 = obtenerasociacion_1(argumentoID, obtenerasociacion_1_arg2, clnt);
				if (result_2 == (leer *) NULL) {
					clnt_perror (clnt, "call failed");
				}
			break;

			case 3:
			 	printf("ID:\n");
			    scanf("%d",&argumentoID);
			    printf("Clave:\n");
			    scanf("%s",&borrarasociacion_1_arg2);
			    printf("ASOCIACION A BORRAR: ID: %d CLAVE: %s .\n",argumentoID,&borrarasociacion_1_arg2);

				result_3 = borrarasociacion_1(argumentoID, borrarasociacion_1_arg2, clnt);
				if (result_3 == (existe *) NULL) {
					clnt_perror (clnt, "call failed");
				}
			break;

			case 4:
				printf("ID:\n");
			    scanf("%d",&argumentoID);
			    printf("Claves y Valores a Enumerar: ID: %d .\n",argumentoID);
				result_4 = enumerar_1(argumentoID, clnt);
				if (result_4 == (resultado *) NULL) {
					clnt_perror (clnt, "call failed");
				}
			break;

			default:
			    printf("Opcion Incorrecta .\n");
			break;

		}


	}	
#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}


int
main (int argc, char *argv[])
{
	char *host;

	if (argc < 2) {
		printf ("usage: %s server_host\n", argv[0]);
		exit (1);
	}
	host = argv[1];
	asociacion_1 (host);
exit (0);
}