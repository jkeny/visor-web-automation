pipeline {
    agent any

    tools {
        // Enlaza el Maven 'M3' que configuramos en los pasos anteriores
        maven 'M3' 
    }

    parameters {
        // Te permite cambiar el navegador y la ruta del test desde la interfaz de Jenkins
        string(name: 'BROWSER', defaultValue: 'CHROME', description: 'Navegador de ejecución')
        string(name: 'TEST_SUITE', defaultValue: 'pe.visorweb.test.login.*Test', description: 'Patrón de pruebas a ejecutar')
    }

    stages {
        stage('Limpiar Entorno') {
            steps {
                echo 'Limpiando el espacio de trabajo...'
                cleanWs() 
            }
        }

        stage('Ejecutar Pruebas Automatizadas') {
            steps {
                echo "Iniciando pruebas en el navegador: ${params.BROWSER}"
                
                // Usamos 'bat' porque tu servidor Jenkins se encuentra corriendo sobre Windows 11
                bat "mvn clean test -Dtest=${params.TEST_SUITE} -Dbrowser=${params.BROWSER}"
            }
        }
    }

    post {
        always {
            echo 'Generando y publicando Reporte de Allure...'
            
            // Invoca al plugin de Allure usando la herramienta 'allure-latest' que guardamos
            allure includeProperties: false, 
                   jdk: '', 
                   properties: [], 
                   reportBuildPolicy: 'ALWAYS', 
                   results: [[path: 'target/allure-results']],
                   commandline: 'allure-latest'
        }
    }
}
