cd Interfaces 
"C:\Program Files\Java\jdk1.7.0_80\bin\javac" -cp ..  *.java
cd ..

cd Servidor 
"C:\Program Files\Java\jdk1.7.0_80\bin\javac" -cp ..  *.java
start C:\Users\sersoker\Dropbox\Universidad\DSD\Practica3\EjemploGuion\src\Servidor\lanza_servidor.bat	

cd Cliente 
"C:\Program Files\Java\jdk1.7.0_80\bin\javac" -cp ..  *.java
start C:\Users\sersoker\Dropbox\Universidad\DSD\Practica3\EjemploGuion\src\Cliente\lanza_cliente.bat
start C:\Users\sersoker\Dropbox\Universidad\DSD\Practica3\EjemploGuion\src\Cliente\lanza_cliente_1.bat
cd ..
