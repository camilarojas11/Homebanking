const app = new Vue({
    el: '#app',
    data: { //
        email: "",
        firstName: "",
        lastName: "",
        outPut: "", //output es una variable q hace una cadena de string
        clients: []
    },
    methods:{
        // load and display JSON sent by server for /clients
        loadData: function() { //funciones
            //Podría usarse fetch pero acá usamos axios para poder hacer las peticiones https.
            axios.get("/clients") //desde frontend solicito info al backend -- axios es una libreria importada en el html que sirve para hacer consultas a la api generada en el repositorio
            .then(function (response) { //en el caso que la respuesta sea favorable debería venir la rta con los datos (puedo verlo en el postman o en el localhost:8080, viene en formato json
                // handle success
                app.outPut = response.data;
                app.clients = response.data._embedded.clients;
            })
            .catch(function (error) {
                // handle error
                app.outPut = error;
            })
        },
         // handler for when user clicks add client
        addClient: function() { //funcion que ve si es mayor a uno
            if (app.email.length > 1 && app.firstName.length > 1 && app.lastName.length > 1) {
                this.postClient(app.email,app.firstName,app.lastName); //manda al cliente
            }
        },
        // code to post a new client using AJAX
        // on success, reload and display the updated data from the server
        postClient: function(email, firstName, lastName) { //coincide con los datos de HomebanckingApplication
             axios.post("clients",{ "email":email, "firstName": firstName, "lastName": lastName }) //envia los datos (.post). Lo que está entre comillas debe corresponder con los nombres de los atributos de las clases
             //Alias: entre comillas (debe coincidir con los atributos de las clases); valor: viene dado por los atributos que están entre parentesis al lado de function.
            .then(function (response) {
                // handle success
                showOutput = "Saved -- reloading";
                app.loadData();
                app.clearData();
            })
            .catch(function (error) {
                // handle error
                app.outPut = error;
            })
        },
        clearData: function(){
            app.firstName = "";
            app.lastName = "";
            app.email = "";
        }
    },
    mounted(){
        this.loadData();
    }
});
