#include "asociaciones.h"

Conjunto *conjuntoPrincipal = NULL;

Existe * ponerasociacion_1_svc(int arg1, char *arg2, char *arg3,  struct svc_req *rqstp){
	static Existe  result;
	
	//Si no existe un conjunto inicial, se  crea
	if(conjuntoPrincipal == NULL){
		printf("Creando Conjunto Principal\n");
		//Reservamos espacio para el conjunto y ponemos la ID
		conjuntoPrincipal = (Conjunto*)malloc(sizeof(Conjunto));
		conjuntoPrincipal->ID=arg1;
		conjuntoPrincipal->siguienteConjunto=NULL;
		//Reservamos espacio para la asociacion y ponemos sus valores
	    conjuntoPrincipal->a=(Asociacion*)malloc(sizeof(Asociacion));	
		conjuntoPrincipal->a->c=strdup(arg2);
		conjuntoPrincipal->a->v=strdup(arg3);
		conjuntoPrincipal->a->siguienteAsociacion=NULL;
	
		printf("Valores Introducidos: ID: %d Clave: %s Valor: %s \n",conjuntoPrincipal->ID,conjuntoPrincipal->a->c,conjuntoPrincipal->a->v);
		result=Fal;
	}
	else{
		//Si la lista principal existe tenemos que buscar si existe la ID que nos pasan como parametro
		//Declaramos un auxiliar para ubicar el conjunto que deseamos
		Conjunto * conjuntoActual=conjuntoPrincipal;
		int encontrado=0;

		while(conjuntoActual!=NULL && encontrado==0){
			if(conjuntoActual->ID == arg1) //Si la ID del conjunto coincide, salimos
				encontrado=1;
			else if(conjuntoActual->siguienteConjunto!=NULL)  //Si no coincide y tenemos un conjunto al que ir, vamos
				conjuntoActual = conjuntoActual->siguienteConjunto;
			else{ //Si no existe conjunto al que ir, y la ID no ha coincidido, ponemos el conjunto a NULL y salimos
				conjuntoActual=NULL;	
				encontrado=1;	
			}
		}

		if(conjuntoActual==NULL){ //Si no hemos encontrado una ID añadimos elvalor		
			printf("ID no existe, añadiendo entrada\n");
			conjuntoActual->siguienteConjunto = (Conjunto*)malloc(sizeof(Conjunto));
			conjuntoActual = conjuntoPrincipal->siguienteConjunto;
			conjuntoActual->ID=arg1;
			conjuntoActual->siguienteConjunto=NULL;
			//Reservamos espacio para la asociacion y ponemos sus valores
		    conjuntoActual->a=(Asociacion*)malloc(sizeof(Asociacion));	
			conjuntoActual->a->c=strdup(arg2);
			conjuntoActual->a->v=strdup(arg3);
			conjuntoActual->a->siguienteAsociacion=NULL;
			if(conjuntoActual->a->siguienteAsociacion==NULL)
			printf("apunta a NULL\n");
		
			printf("Valores Introducidos: ID: %d Clave: %s Valor: %s \n",conjuntoActual->ID,conjuntoActual->a->c,conjuntoActual->a->v);
			result=Fal;
		}
		else{ //Si hemos encontrado una ID, miramos la asociacion, para ver si existe y modificar su valor, o en caso contrario añadirla simplemente
			Asociacion * asociacionActual=conjuntoActual->a; //La asociacion actual, será la encontrada con la ID
			int encontrado2=0;

			while(asociacionActual!=NULL && encontrado2==0){
				printf("Clave actual: %s Clave buscada: %s \n",asociacionActual->c,arg2);

				if(strcmp(asociacionActual->c, arg2)==0) {//Si la clave coincide, salimos
					encontrado2=1;
				}
				else if(asociacionActual->siguienteAsociacion!=NULL){  //Si no coincide y tenemos una asociacion a la que ir, vamos
					asociacionActual = asociacionActual->siguienteAsociacion;
				}
				else{ //Si no existe donde ir, y la clave no ha coincidido, ponemos la asociacion a NULL y salimos
					asociacionActual=NULL;	
					encontrado2=1;	

				}
			}

			if(asociacionActual==NULL){ //Si no hemos encontrado una asociacion que coincida	
				printf("ID existe,clave no existe, creando nueva Asociacion\n");
				//Nos movemos a la asociacion del conjunto actual
				asociacionActual=conjuntoActual->a;
				//Creamos una nueva asociacion siguiente
				asociacionActual->siguienteAsociacion=((Asociacion*)malloc(sizeof(Asociacion)));
				//Nos posicionamos en ella y asignamos valores
				asociacionActual=asociacionActual->siguienteAsociacion;
				asociacionActual->c=strdup(arg2);
				asociacionActual->v=strdup(arg3);
				asociacionActual->siguienteAsociacion=NULL;
			
				printf("Valores Introducidos: Clave: %s Valor: %s \n",asociacionActual->c,asociacionActual->v);
				result=Fal;
			}
			else{ 
				printf("ID existe,clave existe, modificando valor\n");
				asociacionActual->v=strdup(arg3);
				printf("Valores Modificados: Clave: %s Valor: %s \n",asociacionActual->c,asociacionActual->v);
				result=Ver;
			}

		}
	}

	return &result;
}

Leer * obtenerasociacion_1_svc(int arg1, char *arg2,  struct svc_req *rqstp){
	static Leer  result;

	result.errno=Fal;
	if(conjuntoPrincipal!=NULL){
		Conjunto * conjuntoActual=conjuntoPrincipal;
		int encontrado=0;

		while(conjuntoActual!=NULL && encontrado==0){
			if(conjuntoActual->ID == arg1) //Si la ID del conjunto coincide, salimos
				encontrado=1;
			else if(conjuntoActual->siguienteConjunto!=NULL)  //Si no coincide y tenemos un conjunto al que ir, vamos
				conjuntoActual = conjuntoActual->siguienteConjunto;
			else{ //Si no existe conjunto al que ir, y la ID no ha coincidido, ponemos el conjunto a NULL y salimos
				conjuntoActual=NULL;	
				encontrado=1;	
			}
		}

		if(conjuntoActual!=NULL){//Si hemos encontrado una ID, miramos la asociacion, para ver si existe y modificar su valor, o en caso contrario añadirla simplemente
			printf("Obteniendo asociacion\n");

			Asociacion * asociacionActual=conjuntoActual->a; //La asociacion actual, será la encontrada con la ID
			int encontrado2=0;

			//Buscamos el valor
			while(asociacionActual!=NULL && encontrado2==0){
				printf("Clave actual: %s Clave buscada: %s \n",asociacionActual->c,arg2);

				if(strcmp(asociacionActual->c, arg2)==0) {//Si la clave coincide, salimos
					encontrado2=1;}
				else if(asociacionActual->siguienteAsociacion!=NULL){  //Si no coincide y tenemos una asociacion a la que ir, vamos
					asociacionActual = asociacionActual->siguienteAsociacion;}
				else{ //Si no existe donde ir, y la clave no ha coincidido, ponemos la asociacion a NULL y salimos
					asociacionActual=NULL;	
					encontrado2=1;}
			}

			if(asociacionActual!=NULL){
				printf("ID existe,clave existe,\n");
				result.Leer_u.v=&asociacionActual->v;
				result.errno=Ver;	
			}

		}
	}
	if(result.errno==Ver)
	printf("Funcion de obtener asociaciones\n");
	return &result;
}

void borraAsociacion(Asociacion * asociacionAborrar){
	printf("BORRANDO ASOCIACION\n");
	if(asociacionAborrar->siguienteAsociacion==NULL)

    free(asociacionAborrar->c);
    free(asociacionAborrar->v);
    free(asociacionAborrar);
}

void borraConjunto(Conjunto * conjuntoAborrar){
	printf("BORRANDO CONJUNTO\n");
	//Si el conjunto a borrar es el principal, hacemos que el principal apunte a NULL y luego borramos, de esta manera la primera comprobacion al añadir elementos se hará correctamente
	if(conjuntoAborrar==conjuntoPrincipal)
	conjuntoPrincipal=NULL;

    free(conjuntoAborrar);
    free(conjuntoAborrar->siguienteConjunto);
}


Existe * borrarasociacion_1_svc(int arg1, char *arg2,  struct svc_req *rqstp){
	static Existe  result;
	result=Fal;
	/*****************************************************************/	
	/*Buscando el conjunto que coincide con la ID pasada como parametro/	
	/*****************************************************************/	
	if(conjuntoPrincipal!=NULL){
		Conjunto * conjuntoActual=conjuntoPrincipal;
		Conjunto * conjuntoAnterior=conjuntoPrincipal;
		int encontrado=0;

		while(conjuntoActual!=NULL && encontrado==0){
			if(conjuntoActual->ID == arg1) //Si la ID del conjunto coincide, salimos
				encontrado=1;
			else if(conjuntoActual->siguienteConjunto!=NULL){  //Si no coincide y tenemos un conjunto al que ir, vamos
				conjuntoAnterior==conjuntoActual;
				conjuntoActual = conjuntoActual->siguienteConjunto;
				}
			else{ //Si no existe conjunto al que ir, y la ID no ha coincidido, ponemos el conjunto a NULL y salimos
				conjuntoActual=NULL;	
				encontrado=1;	
			}
		}

	/*******************************************************************/	
	/*Bucle que borra la asociacion con clave igual a la recibida por parametro
	/*******************************************************************/
	if(conjuntoActual!=NULL){	
		int encontrado2=0;
		Asociacion * asociacionActual=conjuntoActual->a; //La asociacion actual, será la encontrada con la ID
		Asociacion * asociacionAnterior=conjuntoActual->a; //La asociacion anterior, usada para hacer la union al borrar
		if(asociacionActual->siguienteAsociacion==NULL)
			printf("Aqui vale NULL\n");		
		//Buscamos que tenemos que borrar
		while(asociacionActual!=NULL && encontrado2==0){
			if(strcmp(asociacionActual->c, arg2)==0) {//Si la clave coincide, salimos
				encontrado2=1;
			}
			else if(asociacionActual->siguienteAsociacion!=NULL){  //Si no coincide y tenemos una asociacion a la que ir, vamos
				asociacionAnterior = asociacionActual;
				asociacionActual = asociacionActual->siguienteAsociacion;
			}
			else{ //Si no existe donde ir, y la clave no ha coincidido, ponemos la asociacion a NULL y salimos
				asociacionActual=NULL;	
				encontrado2=1;
			}
		}
		//Si llegamos aqui, hay que borrar una asociacion.
		if(asociacionActual!=NULL){ 
			printf("Hay asociaciones a borrar\n");		
			//Caso 1, la asociacion a borrar y la anterior coinciden, por lo tanto el conjunto actual debe apuntar a donde apunta la actual, sea otra asociacion o NULL
			if(asociacionActual==asociacionAnterior){
				printf("Coincide la primera\n");						
				conjuntoActual->a=asociacionActual->siguienteAsociacion; //Si coincide es el la primera asociacion, hay que conectar el conjunto con la siguiente asociacion (en este caso si es NULL no hay problema)
				borraAsociacion(asociacionActual);
				if(conjuntoActual->a==NULL){
					printf("El conjunto debe borrarse\n");
					conjuntoAnterior->siguienteConjunto=conjuntoActual->siguienteConjunto;
					borraConjunto(conjuntoActual);
				}
			}
			//Caso 2, la asociacion a borrar apunta a NULL u otra asociacion, hacemos que la anterior apunte y borramos
			else{
				asociacionAnterior->siguienteAsociacion=asociacionActual->siguienteAsociacion;
				borraAsociacion(asociacionActual);
			}
		//Indicamos que se ha borrado algo
		result=Ver;	
		}
	}
	return &result;
	}
}	

Resultado * enumerar_1_svc(int arg1,  struct svc_req *rqstp){
	static Resultado  result;


	result.errno=Fal;
	if(conjuntoPrincipal!=NULL){
		Conjunto * conjuntoActual=conjuntoPrincipal;
		int encontrado=0;
				printf("RESULTADO ES:\n");

		while(conjuntoActual!=NULL && encontrado==0){
			if(conjuntoActual->ID == arg1){ //Si la ID del conjunto coincide, salimos
				encontrado=1;
				
				}
			else if(conjuntoActual->siguienteConjunto!=NULL)  //Si no coincide y tenemos un conjunto al que ir, vamos
				conjuntoActual = conjuntoActual->siguienteConjunto;
			else{ //Si no existe conjunto al que ir, y la ID no ha coincidido, ponemos el conjunto a NULL y salimos
				conjuntoActual=NULL;	
				encontrado=1;	
			}
		}

		if(conjuntoActual!=NULL){//Si hemos encontrado una ID, miramos la asociacion, para ver si existe y modificar su valor, o en caso contrario añadirla simplemente
			Asociacion * asociacionActual=conjuntoActual->a; //La asociacion actual, será la encontrada con la ID

			if(asociacionActual!=NULL){
				printf("ID existe,\n");
				result.Resultado_u.resultado= asociacionActual;
				printf("ID existe,\n");
			
			}

		}
	result.errno=Ver;	
	}

	return &result;
}
