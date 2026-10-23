pipeline {
    agent any

    tools {
        maven 'M3' 
    }

    parameters {
        //string(name: 'BROWSER', defaultValue: 'CHROME', description: 'Navegador de ejecución')
        //choice(name: 'BROWSER', choices: ['CHROME', 'FIREFOX', 'EDGE'], description: 'Selecciona el navegador para la ejecución de las pruebas')
        string(name: 'TEST_SUITE', defaultValue: 'pe.visorweb.test.login.*Test', description: 'Patrón de pruebas a ejecutar')
    }

    stages {
        stage('Limpiar y Descargar Código') {
            steps {
                // 1. Limpiamos primero el espacio de trabajo de ejecuciones viejas
                cleanWs()
                
                // 2. Descargamos el código fresco de GitHub después de haber limpiado
                checkout scm
            }
        }

/*        stage('Ejecutar Pruebas Automatizadas') {
            steps {
                echo "Iniciando pruebas en el navegador: ${params.BROWSER}"
                bat "mvn clean test -Dtest=${params.TEST_SUITE} -Dbrowser=${params.BROWSER}"
            }
        }*/
         stage('Pruebas en CHROME') {
            steps {
                echo "Lanzando suite de pruebas en: CHROME"
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    // El primero SI lleva clean para limpiar el historial antiguo
                    bat "mvn clean test -Dtest=${params.TEST_SUITE} -Dbrowser=CHROME"
                }
            }
        }

/*        stage('Pruebas en FIREFOX') {
            steps {
                echo "Lanzando suite de pruebas en: FIREFOX"
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    // Los siguientes NO llevan clean para acumular resultados
                    bat "mvn test -Dtest=${params.TEST_SUITE} -Dbrowser=FIREFOX"
                }
            }
        }*/
        
        stage('Pruebas en EDGE') {
            steps {
                echo "Lanzando suite de pruebas en: EDGE"
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    // Mantiene el acumulado de evidencias
                    bat "mvn test -Dtest=${params.TEST_SUITE} -Dbrowser=EDGE"
                }
            }
        }
    }

    post {
        always {
            echo 'Generando y publicando Reporte de Allure...'
            allure includeProperties: false, 
                   jdk: '', 
                   properties: [], 
                   reportBuildPolicy: 'ALWAYS', 
                   results: [[path: 'target/allure-results']],
                   commandline: 'allure-latest'
        }
    }
}
