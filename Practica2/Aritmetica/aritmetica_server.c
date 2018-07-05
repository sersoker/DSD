#include "aritmetica.h"

int *
sum_1_svc(int arg1, int arg2,  struct svc_req *rqstp)
{
	static int  result;

	result=arg1+arg2;

	return &result;
}

int *
mul_1_svc(int arg1, int arg2,  struct svc_req *rqstp)
{
	static int  result;

	result=arg1*arg2;

	return &result;
}

int *
res_1_svc(int arg1, int arg2,  struct svc_req *rqstp)
{
	static int  result;

	result=arg1-arg2;

	return &result;
}

int *
div_1_svc(int arg1, int arg2,  struct svc_req *rqstp)
{
	static int  result;

	result=arg1/arg2;
	
	return &result;
}
