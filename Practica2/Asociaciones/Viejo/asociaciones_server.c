/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "asociaciones.h"

conjuntoAsociaciones *listaAsociacionesInicial = NULL;


existe *ponerasociacion_1_svc(int arg1, char *arg2, char *arg3,  struct svc_req *rqstp){
	static existe  result;
	asociacion *claveActual;
	conjuntoAsociaciones *asociacionActual;

	printf("ENTRA");	
	
	//Si no hay ninguna asociación
	if(listaAsociacionesInicial == NULL){
		//Si no existe la lista inicialmente, generamos espacio para guardarla y posteriormente insertamos los datos
		listaAsociacionesInicial = (conjuntoAsociaciones*)malloc(sizeof(conjuntoAsociaciones));
		listaAsociacionesInicial->ID = arg1;
		listaAsociacionesInicial->a->c = strdup(arg2);
		listaAsociacionesInicial->a->sigAsociacion = NULL;
		listaAsociacionesInicial->siguienteConjunto = NULL;
		//printf("Datos insertados: Clave: %s; valor: %s\n",asoc->clavesValores->clave,asoc->clavesValores->valor);	
		result=Fal;
	}
	printf("sale");	
	

	return &result;
}

leer *
obtenerasociacion_1_svc(int arg1, char *arg2,  struct svc_req *rqstp)
{
	static leer  result;

	/*
	 * insert server code here
	 */

	return &result;
}

existe *
borrarasociacion_1_svc(int arg1, char *arg2,  struct svc_req *rqstp)
{
	static existe  result;

	/*
	 * insert server code here
	 */

	return &result;
}

resultado *
enumerar_1_svc(int arg1,  struct svc_req *rqstp)
{
	static resultado  result;

	/*
	 * insert server code here
	 */

	return &result;
}
