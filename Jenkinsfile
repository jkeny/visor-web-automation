pipeline {
    agent any

    tools {
        maven 'M3' 
    }

    parameters {
        //string(name: 'BROWSER', defaultValue: 'CHROME', description: 'Navegador de ejecución')
        choice(name: 'BROWSER', choices: ['CHROME', 'FIREFOX', 'EDGE'], description: 'Selecciona el navegador para la ejecución de las pruebas')
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

        stage('Ejecutar Pruebas Automatizadas') {
            steps {
                echo "Iniciando pruebas en el navegador: ${params.BROWSER}"
                bat "mvn clean test -Dtest=${params.TEST_SUITE} -Dbrowser=${params.BROWSER}"
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
