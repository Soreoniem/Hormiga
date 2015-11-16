package e_Ejercicios_Array_2;//Paquete
import java.util.Scanner;                                                     // Teclado importado

public class Ej_20__Hormiga {
                                          // Esta línea no la tenía
    public static void main(String[] args){
    // se utiliza 'teclado' para introducir datos
        Scanner teclado = new Scanner(System.in);                             // Inicio teclado
        // Información:
            // • Recrear el juego de la Hormiga
            // • Tablero de 10x10
            // • Casillas
                // Vacía
                // terrón
                // vino
                // veneno
            // • Terrones de forma aleatoria (66%)
            // • La hormiga se coloca aleatoria
            // • Efectos de las casillas
                // Sobria
                    // vacía    = normal
                    // terrón   = comerselo
                    // veneno   = no se lo come
                    // vino     = comerselo = borracha (1 a 3 casillas aleatorias)
                // Ebria
                    // vacía    = normal
                    // terrón   = comerselo
                    // veneno   = comerselo = muere
                    // vino     = +borrachera

// INICIO EJERCICIO
    // Variables
            char[][] tablero= new char [12][12];                              // El tablero done se jugará
            int i,j;                                                          // Variables para los 'for'
            char hormiga = '•';                                               // Este punto representa la hormiga
            
            int movimiento = 0;                                               // Te permite caminar 1 sola ve por tirada
            
            int posicionI = 0, posicionJ = 0;                                 // Guarda la posición actual de la hormiga
            
            int direccion = 0;                                                // Te permite introducir la dirección deseada
        
        // Estas son las 8 direcciones disponibles
            int direccionError8 = 0, direccionError9 = 0, direccionError6 = 0, direccionError3 = 0, direccionError2 = 0, direccionError1 = 0, direccionError4 = 0, direccionError7 = 0;
            int controlError = 0;                                             // Evita que te salgas del mapa
            int CasillasRestantes = 0;                                        // Te dice cuanto llevas completado
            
            
            int aleatorio = tablero.length -1;                                // Aleatorio para el NAleatorio_i y NAleatorio_j
            int NAleatorio_i, NAleatorio_j;                                   // La hormiga aparecerá en un punto aleatorio del mapa
            
        // Evita que la hormiga aparezca en el borde del mapa
            do{
                NAleatorio_i = (int)Math.round(Math.random()*aleatorio);
                NAleatorio_j = (int)Math.round(Math.random()*aleatorio);
            } while( NAleatorio_i==0 || NAleatorio_i==tablero.length-1 || NAleatorio_j==0 || NAleatorio_j==tablero.length-1 );
            
        // Terrones
                int[][] terrones= new int[12][12];                            // Matriz en el que se almacenan los terrones
                int estado = 0;                                               // Estado: 0 = Sobria, 1 = Ebria, -1 = Muerta
                int borrachera = 0;                                           // Tiempo de borrachera
                int MasBorrachera = 0;                                        // Aumenta la borrachera
                int ValorTerrones = 0;                                        // Enlaza las 2 matrices: [tableros] y [Matrices]
                int AleatorioTerron10 = 0;                                    // Crea cuatos ¡Hic! aparecen (1-3)
                int AleatorioTerron11 = 0;                                    // Enfado de la hormiga al no encontrar nada (estado borracha)
                int vacio = 0;                                                // Se multiplica por [AleatorioTerron11] para decir cuantos vacíos piensa que ha visitado la hormiga
            
    // Programa
            
        // Crea la matriz [terrones]
            for( i=0 ; i<terrones.length ; i++ ){
                for( j=0 ; j<terrones.length ; j++ ){
                // pone la tabla a 1s excepto los bordes
                    terrones[i][j] = 1;
                    terrones[0][j] = 111;                                     // 111 = margen bloqueado
                    terrones[11][j] = 111;                                    // Necesario un número mayor a 100 (valores para la matriz [terrones])
                    terrones[i][0] = 111;
                    terrones[i][11] = 111;
                    
                // Nº Aleatorios
                // Sustituye los 1s por números del 1-100
                    if( terrones[i][j]==1 ){
                        terrones[i][j] = (int)Math.round(Math.random()*99)+1;
                    }
                
                // Asignar
                // Frecuencia con la que aparecerán los terrones (pasa de los números del 1-100 a 1-4)
                    if( 1<=terrones[i][j] && terrones[i][j]<=6 ){             // Valores para el terrón con veneno
                        terrones[i][j] = 3;
                    } else{
                        if( 7<=terrones[i][j] && terrones[i][j]<=20 ){        // Valores para el terrón con vino
                            terrones[i][j] = 4;
                        } else {
                            if( 21<=terrones[i][j] && terrones[i][j]<=45 ){   // Valores para el "vacío" (el vacío hara que la hormiga opine)
                                terrones[i][j] = 2;
                            } else {
                                if( 46<=terrones[i][j] && terrones[i][j]<=100 ){ // Valores sin nada
                                    terrones[i][j] = 1;
                                }
                            }
                        }
                    }
                }
            }
                
        // Crea la matriz [tablero]
            for( i=0 ; i<tablero.length ; i++ ){
                for( j=0 ; j<tablero.length ; j++ ){
                    
                // Establece la posición e la hormiga
                    if( i==NAleatorio_i && j==NAleatorio_j ){
                        tablero[i][j] = '•';
                        terrones[i][j] = 0;                                   // Según la posición de la hormiga también actualizará la otra matriz
                        posicionI = i;                                        // Recoge la posición de la hormiga
                        posicionJ = j;
                // Se crean los bordes del tablero
                    } else {
                        tablero[0][j] = '═';
                        tablero[tablero.length-1][j] = '═' ;
                        tablero[i][0] = '║';
                        tablero[i][tablero.length-1] = '║';
                // Se establece las esquinas del tablero
                        tablero[0][0] = '╔';
                        tablero[0][tablero.length-1] = '╗';
                        tablero[tablero.length-1][0] = '╠';
                        tablero[tablero.length-1][tablero.length-1] = '╝';
                    }
                    
                // Rellena el resco con '?'
                    if( tablero[i][j]!='•' && tablero[i][j]!='═' && tablero[i][j]!='║' && tablero[i][j]!='╔' && tablero[i][j]!='╗' && tablero[i][j]!='╠' && tablero[i][j]!='╝' ){
                        tablero[i][j] = '?';
                    }
                    
                }
            }
// Comienza el do while que contiene todo
            do{
    // Comienza el do while para la pregunta y el tablero
                do{
                    // MUESTRA TERRONES
                // Descomenta este for y verás la otra matriz [terrones]
                    /*
                        for( i=0 ; i<terrones.length ; i++ ){
                            for( j=0 ; j<terrones.length ; j++ ){
                                if( terrones[i][j]!=111 && terrones[i][j]!=0 ){
                                    System.out.print("[" + terrones[i][j] + "]");
                                } else {
                                    if( terrones[i][j]==0 ){
                                        System.out.print("[ ]");
                                    }
                                }
                            }
                            System.out.print("\n");
                        }
                    */
                    // Muestra tablero
                    CasillasRestantes=100;                                    // Establece el % completado
                    System.out.println("\n\n\n\n\n");                         // Separa un turno de otro
                    for( i=0 ; i<tablero.length ; i++ ){
                        for( j=0 ; j<tablero.length ; j++ ){

                        // Evita [] en los border
                        // Para los '?', la hormaiga '•' y el espacio ' '
                            if( tablero[i][j]=='•' || tablero[i][j]=='?' || tablero[i][j]==' ' ){
                                System.out.print("[" + tablero[i][j] + "]");
                                if( tablero[i][j]=='?' ){
                                    CasillasRestantes--;                      // Resta las casillas por las que has pasado
                                }

                        // Crea las esquinas: '╔' '╗' '╠' '╝'
                            } else {
                                if( tablero[i][j]=='╔' || tablero[i][j]=='╠' ){
                                    System.out.print(tablero[i][j] + "═");
                                } else {
                                    if( tablero[i][j]=='╗' || tablero[i][j]=='╝' ){
                                        System.out.print("═" + tablero[i][j]);

                        // Crea los bordes '║'═'
                                    } else {
                                        if( tablero[i][j]=='═' ){
                                            System.out.print("═" + tablero[i][j] + "═");
                                        } else{
                                            if( j==0 ){
                                                System.out.print(tablero[i][j] + " ");
                                            } else{
                                                System.out.print(" " + tablero[i][j]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        System.out.print("\n");
                    }
                    // Final mostrar tablero
                // Acciones de la hormiga y sus consecuenias
                    if( controlError!=0 ){
                        System.out.println("│ Acciones: ¡HAY!\n│ Hormiga : No puedes salirte de los límites");
                        controlError= 0;
                    } else {
                        if( direccion>9 || direccion<0 || direccion==5 ){
                            System.out.println("│ Acciones: ¿Eh?\n│ Hormiga : ¿Para donde has dicho?");
                        } else {
                        // Terrones
                            if( ValorTerrones==1 ){
                                System.out.println("│ Acciones: Vacío\n│ Hormiga : Bueno, a por la siguiente.");
                                ValorTerrones = 0;
                            } else {
                                
                                if( ValorTerrones==10 ){
                                    AleatorioTerron10 = (int)Math.round(Math.random()*2)+1; // aleatorio de 1-3
                                    System.out.print("│ Acciones: ♫tralari ♪ tralara ♪ ♪");
                                    System.out.print("\n│ Hormiga : ");
                                    for( i=0 ; i<AleatorioTerron10 ; i++ ){
                                        System.out.print(" ¡Hic!");
                                    }
                                    System.out.print("\n");
                                    AleatorioTerron10 = 0;
                                } else {
                                    
                                    if( ValorTerrones==11 ){
                                        AleatorioTerron11 = (int)Math.round(Math.random()*2)+1; // aleatorio de 1-3
                                        AleatorioTerron11 = AleatorioTerron11 *vacio;
                                        
                                        System.out.println("│ Acciones:\n│ Hormiga : ¿VACÍO? ya van " + AleatorioTerron11 + " vacios, ¡Joder!");
                                        
                                    } else {
                                        
                                        if( ValorTerrones==2 ){
                                            System.out.println("│ Acciones: Comido: Terrón\n│ Hormiga : Mmm, rico rico.");
                                        } else {
                                            
                                            if( ValorTerrones==12 ){
                                                System.out.println("│ Acciones: Comido: Terrón\n│ Hormiga : ¡VIINO! mmm... no es vino, pero da igual :)");
                                            } else {
                                                if( ValorTerrones==3 ){
                                                    System.out.println("│ Acciones: Veneno: evitado\n│ Hormiga : Hoy no...¡MAÑANA!");
                                                } else {
                                                    if( ValorTerrones==13 ){
                                                        System.out.println("│ Acciones: Comido: ¡Veneno!\n│ Hormiga : Uuuhh... creo que este no me ha sentado bi...");
                                                        estado = -1;
                                                    } else {
                                                        if( ValorTerrones==4 ){
                                                            System.out.println("│ Acciones: Comido: Vino\n│ Hormiga : Esto sabe algo difer...¡Hip!");
                                                            borrachera = (int)Math.round(Math.random()*3)+2; // aleatorio de 2-5
                                                            estado = 1;
                                                            ValorTerrones = 0;
                                                        } else {
                                                            if( ValorTerrones==14 ){
                                                                System.out.println("│ Acciones: Comido: Vino\n│ Hormiga : El vino que tiene asuncióóóón...");
                                                            } else {
                                                                System.out.println("│ Acciones:\n│ Hormiga :");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if( borrachera==0 ){ // quita el cabreo de la hormiga
                        vacio = 0;
                    }
                    
                    if( estado==1 ){ // Muestra cuantos turnos faltan está sobria la hormiga
                        System.out.println("│ Borrachera restante: " + borrachera);
                    }
                // Menú
                    System.out.println("├──────────────────────────┐");
                    System.out.println("│ ¿Qué dirección escojes?: │");
                // Establece los errores a 0 (no hay errores)
                        direccionError8 = 0; direccionError9 = 0; direccionError6 = 0; direccionError3 = 0; direccionError2 = 0; direccionError1 = 0; direccionError4 = 0; direccionError7 = 0;
                    
                // Evita que la hormiga traspase los bordes del mapa y desactiva visualmente el número
                    if( tablero[posicionI-1][posicionJ-1]=='╔' || tablero[posicionI-1][posicionJ-1]=='═' || tablero[posicionI-1][posicionJ-1]=='║' ){
                        System.out.print("│        [ ]");
                        direccionError7 = 7;
                    } else {
                        System.out.print("│        [7]");
                    }
                    if( tablero[posicionI-1][posicionJ]=='═' ){
                        System.out.print("[ ]");
                        direccionError8 = 8;
                    } else{
                        System.out.print("[8]");
                    }
                    if( tablero[posicionI-1][posicionJ+1]=='╗' || tablero[posicionI-1][posicionJ+1]=='═' || tablero[posicionI-1][posicionJ+1]=='║' ){
                        System.out.println("[ ]         │");
                        direccionError9 = 9;
                    } else {
                        System.out.println("[9]         │");
                    }
                    if( tablero[posicionI][posicionJ-1]=='║' ){
                        System.out.print("│        [ ]");
                        direccionError4 = 4;
                    } else {
                        System.out.print("│        [4]");
                    }
                        System.out.print("   ");

                    if( tablero[posicionI][posicionJ+1]=='║' ){
                        System.out.println("[ ]         │");
                        direccionError6 = 6;
                    } else {
                        System.out.println("[6]         │");
                    }
                    if( tablero[posicionI+1][posicionJ-1]=='╠' || tablero[posicionI+1][posicionJ-1]=='═' || tablero[posicionI+1][posicionJ-1]=='║' ){
                        System.out.print("│        [ ]");
                        direccionError1 = 1;
                    } else {
                        System.out.print("│        [1]");
                    }
                    if( tablero[posicionI+1][posicionJ]=='═' ){
                        System.out.print("[ ]");
                        direccionError2 = 2;
                    } else {
                        System.out.print("[2]");
                    }
                    if( tablero[posicionI+1][posicionJ+1]=='╝' || tablero[posicionI+1][posicionJ+1]=='═' || tablero[posicionI+1][posicionJ+1]=='║' ){
                        System.out.println("[ ]         │");
                        direccionError3 = 3;
                    } else {
                        System.out.println("[3]         │");
                    }
                // 2ª parte el menú
                    // Con la tecla '0' sale del juego (excepto si está borracha, debido a que se bloquea)
                    System.out.println("├──────────────────────────┘");
                    System.out.println("│ [0] Salir        ┌────────────");
                    System.out.println("└───────────┘Completado: " + CasillasRestantes + "%"); // muestra porciento actual
                        direccion = teclado.nextInt();                        // Permite introducir un número
                                                                              // Acción 2: Permite pausar si la hormiga está borracha
                    
                    if( estado==1 ){                                          // Si la hormiga está borracha utiliza una acción aleatoria
                        direccion = (int)Math.round(Math.random()*8)+1; // aleatorio de 1-9
                    }
                    
                    if( direccion==0 ){break;}                                // Sale del juego si no está borracha
                // Permite poner el mensaje de error sin tocar el while y sus variables de error
                // Crea un error si utiliazas una tecla equivocada
                    if( direccionError7==direccion ){
                        controlError = 7;
                    } else {
                        if(  direccionError8==direccion){
                            controlError = 8;
                        } else {
                            if( direccionError9==direccion ){
                                controlError = 9;
                            } else {
                                if( direccionError4==direccion ){
                                    controlError = 4;
                                } else {
                                    if( direccionError6==direccion ){
                                        controlError = 6;
                                    } else {
                                        if( direccionError1==direccion ){
                                            controlError = 1;
                                        } else {
                                            if( direccionError2==direccion ){
                                                controlError = 2;
                                            } else {
                                                if( direccionError3==direccion ){
                                                    controlError = 3;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } // Final del control

// Último Control: Si utilizas un número oculto para avanzar te preguntará e nuevo (poniendo los errores a 0)
                } while( direccionError7==direccion || direccionError8==direccion || direccionError9==direccion || direccionError4==direccion || direccionError6==direccion || direccionError1==direccion || direccionError2==direccion || direccionError3==direccion || direccion>9 || direccion<0 || direccion==5 );
                movimiento = 0;                                               // Empieza a mover
                
                for( i=0 ; i<tablero.length ; i++ ){
                    for( j=0 ; j<tablero.length ; j++ ){
                        
                    // Acciones según la tecla pulsada
                        if( tablero[i][j]=='•' && movimiento==0 ){
                            
                        // Tecla Nº8
                            if( direccion==8 ){
                                if( estado==-1 ){                             // No molesta en el juego pero
                                    tablero[i-1][j] = 'X';                    // por falta de tiempo no puedo
                                } else {                                      // intentaba que al morir la hormiga su simbolo '•' cambiase a 'X'
                                    tablero[i-1][j] = '•';
                                }
                                ValorTerrones = terrones[i-1][j];             // Comprueba que valor tiene a donde se dirige la hormiga
                                
                                if( ValorTerrones==3 ){                       // Si pasa por una casilla de veneno sobria, el veneno no desaparece
                                    terrones[i-1][j] = 3;
                                } else{
                                    terrones[i-1][j] = 0;                     // con una acción anterior crea un espacio por donde pasa la hormiga
                                }
                                posicionI = i-1;                              // Recoge los valores donde pasará la hormiga
                                posicionJ = j;
                                
                                tablero[i][j] = ' ';
                            }
                        // Tecla Nº9
                            if( direccion==9 ){
                                if( estado==-1 ){
                                    tablero[i-1][j+1] = 'X';
                                } else {
                                    tablero[i-1][j+1] = '•';
                                }
                                ValorTerrones = terrones[i-1][j+1];
                                
                                if( ValorTerrones==3 ){
                                    terrones[i-1][j+1] = 3;
                                } else{
                                    terrones[i-1][j+1] = 0;
                                }
                                posicionI = i-1;
                                posicionJ = j+1;
                                
                                tablero[i][j] = ' ';
                            }
                        // Tecla Nº6
                            if( direccion==6 ){
                                if( estado==-1 ){
                                    tablero[i][j+1] = 'X';
                                } else {
                                    tablero[i][j+1] = '•';
                                }
                                ValorTerrones = terrones[i][j+1];
                                
                                if( ValorTerrones==3 ){
                                    terrones[i][j+1] = 3;
                                } else{
                                    terrones[i][j+1] = 0;
                                }
                                posicionI = i;
                                posicionJ = j+1;
                                
                                tablero[i][j] = ' ';
                            }
                        // Tecla Nº3
                            if( direccion==3 ){
                                if( estado==-1 ){
                                    tablero[i+1][j+1] = 'X';
                                } else {
                                    tablero[i+1][j+1] = '•';
                                }
                                ValorTerrones = terrones[i+1][j+1];
                                
                                if( ValorTerrones==3 ){
                                    terrones[i+1][j+1] = 3;
                                } else{
                                    terrones[i+1][j+1] = 0;
                                }
                                posicionI = i+1;
                                posicionJ = j+1;
                                
                                tablero[i][j] = ' ';
                            }
                        // Tecla Nº9
                            if( direccion==2 ){
                                if( estado==-1 ){
                                    tablero[i+1][j] = 'X';
                                } else {
                                    tablero[i+1][j] = '•';
                                }
                                ValorTerrones = terrones[i+1][j];
                                
                                if( ValorTerrones==3 ){
                                    terrones[i+1][j] = 3;
                                } else{
                                    terrones[i+1][j] = 0;
                                }
                                posicionI = i+1;
                                posicionJ = j;
                                
                                tablero[i][j] = ' ';
                            }
                        // Tecla Nº9
                            if( direccion==1 ){
                                if( estado==-1 ){
                                    tablero[i+1][j-1] = 'X';
                                } else {
                                    tablero[i+1][j-1] = '•';
                                }
                                ValorTerrones = terrones[i+1][j-1];
                                
                                if( ValorTerrones==3 ){
                                    terrones[i+1][j-1] = 3;
                                } else{
                                    terrones[i+1][j-1] = 0;
                                }
                                posicionI = i+1;
                                posicionJ = j-1;
                                
                                tablero[i][j] = ' ';
                            }
                        // Tecla Nº9
                            if( direccion==4 ){
                                if( estado==-1 ){
                                    tablero[i][j-1] = 'X';
                                } else {
                                    tablero[i][j-1] = '•';
                                }
                                ValorTerrones = terrones[i][j-1];
                                
                                if( ValorTerrones==3 ){
                                    terrones[i][j-1] = 3;
                                } else{
                                    terrones[i][j-1] = 0;
                                }
                                posicionI = i;
                                posicionJ = j-1;
                                
                                tablero[i][j] = ' ';
                            }
                        // Tecla Nº9
                            if( direccion==7 ){
                                if( estado==-1 ){
                                    tablero[i-1][j-1] = 'X';
                                } else {
                                    tablero[i-1][j-1] = '•';
                                }
                                ValorTerrones = terrones[i-1][j-1];
                                
                                if( ValorTerrones==3 ){
                                    terrones[i-1][j-1] = 3;
                                } else{
                                    terrones[i-1][j-1] = 0;
                                }
                                posicionI = i-1;
                                posicionJ = j-1;
                                
                                tablero[i][j] = ' ';
                            }
                            movimiento = 1;                                   // Finaliza el turno
                            
                        }
                    }
                    
                }
                
        // efectos de los terrones
            // Sobria
                // Podría quitarlo pero cuando programo la hormiga me parece más útil tenerlo
                if( estado==0 ){
                    if( ValorTerrones==0 ){
                        // no hagas nada
                    } else {
                        if( ValorTerrones==1 ){
                            ValorTerrones = 1;
                            // mensaje: "VACIO"
                            // Hormiga: Bueno, a por la siguiente
                        } else {
                            if( ValorTerrones==2 ){
                                ValorTerrones = 2;
                                // Mensaje: "COMIDO: Terron"
                                // Hormiga: mmm, rico rico
                            } else {
                                if( ValorTerrones==3 ){
                                    ValorTerrones = 3;
                                    // Mensaje: "VENENO: Evitado"
                                    // Hormiga: Hoy no...¡MAÑANA!
                                } else {
                                    if( ValorTerrones==4 ){
                                        ValorTerrones = 4;
                                        // Mensaje: "COMIDO: Vino"
                                        // borrachera = aleatorio 2-3
                                        // Hormiga: Esto sabe algo difer...¡Hip!
                                    }
                                }
                            }
                        }
                    }
                }
            // Ebria
                // al salir de borracha poner vacio = 0
                if( estado==1 ){
                    ValorTerrones = ValorTerrones +10;
                    
                    if( ValorTerrones==10 ){
                        ValorTerrones = 10;
                        borrachera = borrachera -1;
                    } else {
                        if( ValorTerrones==11 ){
                            ValorTerrones = 11;
                            vacio = vacio +1;
                            borrachera = borrachera -1;
                        } else {
                            if( ValorTerrones==12 ){
                                ValorTerrones = 12;
                                // Mensaje: "COMIDO: Terrón"
                                // Hormiga: "¡VIINO! mmm... no es vino pero da igual"
                                borrachera = borrachera -1;
                            } else {
                                if( ValorTerrones==13 ){
                                    ValorTerrones = 13;
                                    // Mensaje: "COMIDO: ¡Veneno!"
                                    // Hormiga: Uuuhh...creo que este no me ha sentado bi...
                                    // estado = -1;
                                } else {
                                    if( ValorTerrones==14 ){
                                        ValorTerrones = 14;
                                        MasBorrachera = (int)Math.round(Math.random()*2)+1; // aleatorio de 1-3
                                        borrachera = borrachera + MasBorrachera;
                                        // Mensaje: "COMIDO: VINO"
                                        // Hormiga: El vino que tiene asuncióóóón....
                                        // borrachera = borrachera + aleatorio de 1-2
                                    }
                                }
                            }
                        }
                    }
                }
                
                if( borrachera==0 ){                                         // Si se le pasa la borrachera cambia al estado sobria
                    estado = 0;                                              // si tubiese un poco más de tiempo pondría la opciónde "Resaca"
                }                                                               // Haría que la hormiga solo pudiese mover con 8, 6, 2 y 4 (sin forma diagonal)
                if( estado==-1 || CasillasRestantes==100 ){
                    break;                                                   // Sale del juego cuando se completa o muere la hormiga
                }
            } while( CasillasRestantes != 100 || direccion==8 || direccion==9 || direccion==6 || direccion==3 || direccion==2 || direccion==1 || direccion==4 || direccion==7 );
    // Resultado
            if( estado==-1 ){                                                // Cuando pierdes el juego
                System.out.println("FIN DE LA PARTIDA");
            }
            if( CasillasRestantes==100 ){                                    // Si consigues el 100% del juego
                System.out.println("¡JUEGO COMPLETADO!");
            }
        
// ↓↓ ‼No tocar estas llaves‼ ↓↓
        // lave de sonido
    }
}