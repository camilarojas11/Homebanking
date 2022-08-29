//Introducir SETTIMEOUT (funcion de js) con milisegundos - asignar una duracion a las cookies para que a los 5 min se desloguee (que se redirija al apilogout)
//QUE TE REDIRIJA AL LOGOUT, Y DEL LADO FRONTEND COMO HACER QUE CUANDO QUEDA INACTIVO TOTALMENTE SE DESLOGUEE, Y PONER A LOS 3 MIN MUESTRE UN CARTEL DE ESTAS AHI?
var app = new Vue({
    el:"#app",
    data:{
        email:"",
        password:"",
        firstName: "",
        lastName: "",
        errorToats:null,
        errorMsg: "",
        showSignUp: false,
    },
    methods:{
        signIn: function(event){
            event.preventDefault();
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }
            axios.post('/api/login',`email=${this.email}&password=${this.password}`,config)
                .then(response => window.location.href="/web/accounts.html") //me redirige a la pag de cuentas
                .catch(() =>{
                    this.errorMsg = "Sign in failed, check the information"
                    this.errorToats.show();
                })
        },
        signUp: function(event){
            event.preventDefault();
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }
            axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,config)
                .then(() => { this.signIn(event) })
                .catch(() =>{
                    this.errorMsg = "Sign up failed, check the information"
                    this.errorToats.show();
                })
        },
        showSignUpToogle: function(){
            this.showSignUp = !this.showSignUp;
        },
        formatDate: function(date){
            return new Date(date).toLocaleDateString('en-gb');
        }
    },
    mounted: function(){
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
    }
})