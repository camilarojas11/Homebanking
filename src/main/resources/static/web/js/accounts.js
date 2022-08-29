  var app = new Vue({
      el:"#app",
      data:{
          clientInfo: {},
          errorToats: null,
          errorMsg: null,
      },
      methods:{
          getData: function(){
              axios.get("/api/clients/current")
                  .then((response) => {
                      //get client ifo
                      this.clientInfo = response.data;
                  })
                  .catch((error)=>{
                      // handle error
                      this.errorMsg = "Error getting data";
                      this.errorToats.show();
                  })
          },
          formatDate: function(date){
              return new Date(date).toLocaleDateString('en-gb');
          },
          signOut: function(){
              axios.post('/api/logout')
                  .then(response => window.location.href="/web/index.html")
                  .catch(() =>{
                      this.errorMsg = "Sign out failed"
                      this.errorToats.show();
                  })
          },
          create: function(){
              axios.post('http://localhost:8080/api/clients/current/accounts')
                  .then(response => window.location.reload())
                  .catch((error) =>{
                      this.errorMsg = error.response.data;
                      this.errorToats.show();
                  })
          }
      },
      mounted: function(){
          this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
          this.getData();
      }

  })

var time = 300000 ; //session timeout 10 seconds for the tests
var timeout;

  document.addEventListener('mousemove', function () {
   console.log("abc")
            clearTimeout(timeout);
          timeout = setTimeout(function() {
              alert("you´re going to log out!");
              axios.post("/api/logout")
              window.location.href="/web/index.html"
          },time);



      });