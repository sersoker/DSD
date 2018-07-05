const TAMSTRING=500;
typedef string clave<TAMSTRING>;
typedef string valor<TAMSTRING>;

/* Tipo dato existe/noexiste para saber si al poner una asociacion ya existia, tambien contorla si se borra o no una asociacion*/
enum Existe{
	Ver=0, 
	Fal=1
};

/* Cada conjunto de asociaciones con una ID comun, se almacenan como una estructura enlazada*/
struct Asociacion{
	clave  c;
	valor  v;	
	Asociacion * siguienteAsociacion;
};

/*Cada conjunto de asociaciones, tiene una id, un conjunto de asociaciones enlazadas y un enlace al siguiente conjunto*/
struct Conjunto{
	int ID;
	Asociacion * a;
	Conjunto * siguienteConjunto;
};

/*Controla si se devuelve un valor o no*/
union Leer switch (int errno) {
	case 0:
		valor * v;
	default:
		void;
};

/*controla si se devuelve una asociacion o no*/
union Resultado switch (int errno) {
	case 0:
		Asociacion * resultado;
	default:
		void;
};


program ASOCIACION{
	version ASOCIACION_VER{
		Existe ponerAsociacion(int, string, string)=1;
		Leer ObtenerAsociacion(int, string)=2;
		Existe BorrarAsociacion(int, string)=3;
		Resultado Enumerar(int)=4;
	}=1;
}=0x20000001;
