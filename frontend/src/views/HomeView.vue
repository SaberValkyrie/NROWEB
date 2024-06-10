<template>
<div class="ui-bg-cover ui-bg-overlay-container text-white" style="background-color: #87CEFA;">
    <div class="ui-bg-overlay bg-dark opacity-50"></div>
    <div class="container">
      <div class="text-center py-5">
        <img  :src="!loggedInUser ? 'https://static.thenounproject.com/png/363640-200.png' : (loggedInUser.gender == 0 ? avttd : (loggedInUser.gender == 1 ? avtnm : avtxd))" alt="" class="ui-w-100 rounded-circle">

        <div class="col-md-8 col-lg-6 col-xl-5 p-0 mx-auto" v-if="loggedInUser">
          <h4 class="font-weight-bold my-4">{{loggedInUser ? loggedInUser.name : (!myAccount ? 'Bạn chưa tạo nhân vật' : '' )}} </h4>
          <div class="text-center">
            <a class="d-inline-block text-white ml-3">
            Số Dư: <strong>{{myAccount.sodu}}</strong>
            <span class="opacity-75"> VNĐ</span>

          </a>  
          </div>
        </div>
        <br>
        <div class="text-center" v-if="loggedInUser">
            <button @click="go('/changepassword')" class="action">Đổi Mật Khẩu</button>
            <button @click="go('/naptien')" class="action">Nạp Tiền</button>
            <button @click="logout()" class="action">Đăng Xuất</button>
        </div>
        <div class="text-center" v-else>
            <button @click="go('/login')" class="action">Đăng Nhập</button>
            <button  @click="go('/register')" class="action">Đăng Ký</button>
        </div>
<br>
        <div class="text-center">
            <img class="concac" src="https://ngocrongonline.com/images/android.png">
            <img class="concac" src="https://ngocrongonline.com/images/pc.png">
            <img class="concac" src="https://ngocrongonline.com/images/ip.png">
        </div>
      </div>
    </div>


    <br> 

    <div class="ui-bg-overlay-container">
      <div class="ui-bg-overlay bg-dark opacity-25"></div>
      <ul class="nav nav-tabs tabs-alt justify-content-center border-transparent">
        
        <li class="nav-item">
          <a :class="type == 0 ? 'nav-link text-white bold py-4 active' : 'nav-link text-white bold py-4'"
          @click="changeType(0)"
          >Bài Đăng</a>
        </li>

        <li class="nav-item">
            <a           @click="changeType(1)" :class="type == 1 ? 'nav-link text-white bold py-4 active' : 'nav-link text-white bold py-4'">Báo Lỗi</a>
        </li>

        <li class="nav-item">
            <a           @click="changeType(2)" :class="type == 2 ? 'nav-link text-white bold py-4 active' : 'nav-link text-white bold py-4'">Tố Cáo</a>
        </li>

        <li class="nav-item">
            <a           @click="changeType(3)" :class="type == 4 ? 'nav-link text-white bold py-4 active' : 'nav-link text-white bold py-4'">Góp ý</a>
        </li>
      </ul>


      <br>    <br>    <br>    <br>








      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.0/css/boxicons.min.css" integrity="sha512-pVCM5+SN2+qwj36KonHToF2p1oIvoU3bsqxphdOIWMYmgr4ZqD3t5DjKvvetKhXGc/ZG5REYTT6ltKfExEei/Q==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/MaterialDesign-Webfont/5.3.45/css/materialdesignicons.css" integrity="sha256-NAxhqDvtY0l4xn+YVa6WjAcmd94NNfttjNsDmNatFVc=" crossorigin="anonymous" />

<div class="container">
  
    <div class="row">
        <div class="col-xl-3 col-sm-6"  v-for="post in listPost">
            <div class="card">
                <div class="card-body">
                    <div class=" float-end">
                        <img v-if="post.islock" class='img-icon' src="https://media.discordapp.net/attachments/1225059666573394010/1249689872550330499/image.png?ex=666837e3&is=6666e663&hm=2f30d4f387ea0238af23a60955408b351ef5326fdbd2ac73eb28a73da24edff3&=&format=webp&quality=lossless&width=419&height=419">

                    </div>

                    <div class="d-flex align-items-center">
                        <div>
                            <img :src="post.player.gender == 0 ? avttd : (post.player.gender == 1 ? avtxd : avtnm)" alt="" class="avatar-md rounded-circle img-thumbnail" /></div>
                        <div class="flex-1 ms-3">
                            <h5 class="font-size-18 mb-1">
                                <a class="text-bold">{{post.player.name}}</a>
                            </h5>

                            <!-- <span class="badge badge-soft-success mb-0">Tố Cáo</span> -->
                            <span class="badge badge-soft-danger mb-0">Báo Lỗi</span>

                        </div>
                    </div>
                    <div class="mt-3 pt-1">
                        <p class="text-muted mb-0">
                            <i class="mdi mdi-email font-size-15 align-middle pe-2 text-primary">                                
                            </i> {{ truncated(post.title) }}</p>
                   
                    </div>
                    <div class="d-flex gap-2 pt-4">
                        <button type="button" class="btn btn-soft-primary btn-sm w-50">
                            <i class="fa-solid fa-clock-rotate-left"></i>
                            {{formatCreatedAt(post.createAt)}}</button>
                    </div>
                </div>
            </div>
        </div>
    
    </div>

     <!-- phân trang -->
    <div class="row g-0 align-items-center pb-4" v-if="listPost.length > 0">
        <div class="col-sm-6">
            <div><p class="mb-sm-0"></p></div>
        </div>
        <div class="col-sm-6">
            <div class="float-sm-end">
                <ul class="pagination mb-sm-0">
                    <li class="page-item disabled">
                        <a href="#" class="page-link"><i class="mdi mdi-chevron-left"></i></a>
                    </li>
                    <li class="page-item active"><a href="#" class="page-link">1</a></li>
                    <li class="page-item"><a href="#" class="page-link">2</a></li>
                    <li class="page-item"><a href="#" class="page-link">3</a></li>
                    <li class="page-item"><a href="#" class="page-link">4</a></li>
                    <li class="page-item"><a href="#" class="page-link">5</a></li>
                    <li class="page-item">
                        <a href="#" class="page-link"><i class="mdi mdi-chevron-right"></i></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div v-else>Chưa Có Bài Viết Nào Thuộc Loại Này
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
    </div>
</div>




    </div>
  </div>























<app-footer></app-footer>
</template>
   
<script>
import HelloWorld from '@/components/HelloWorld.vue'
import Header from '@/views/support/Header.vue';
import Footer from '@/views/support/Footer.vue';
import axios from 'axios';
import { baseWeb,baseURL } from '@/router/index';
import { mapGetters } from 'vuex';
import moment from 'moment';
import store from '@/store';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { AccountService } from '@/core/service/accountservice';

export default {
  name: 'Profile',
  computed: {
   ...mapGetters(['loggedInUser']),
 },
created() {
    this.getpost()

    setInterval(() => {
       if(this.loggedInUser){
        this.getpost()
        this.getaccByToken()
       }
}, 1000);
 },


 data() {
   return {
     baseUrl : baseURL,
     token: localStorage.getItem('token'),
     accountService: new AccountService(),
     type: 0,
     listPost:[],
     avtxd:'https://forum.ngocrongonline.com/avatar/522.png',
     avtnm:'https://forum.ngocrongonline.com/avatar/523.png',
     avttd:'https://forum.ngocrongonline.com/avatar/734.png',
     myAccount:{},
    
    
   };
 },

 methods: {

    changeType(t){
        this.type = t;
        this.getpost();
    },
   getpost(){
    this.accountService.getpost(this.type).then(res => {
        this.listPost = res.data.data;
        }).catch(error => {
});
   },

   getaccByToken(){
    this.accountService.getaccByToken(this.token).then(res => {
        this.myAccount = res.data.data;
        }).catch(error => {
 toast.warning(error.response.data.message)
});
   },

   logout() {
    axios.post(`${this.baseUrl}/logout/${this.token}`)
      .then(response => {
        localStorage.clear();
        toast.info('Đăng xuất thành công,vui lòng đăng nhập lại')
        setTimeout(() => {
          window.location.reload();
				}, 1000);
      })
      .catch(error => {
        console.error('Error logging out:', error);
      });
  },


  formatBirthDate(date) {
   if (!date) return ''; // Đảm bảo xử lý trường hợp ngày sinh không tồn tại
   return moment(date).format('YYYY-MM-DD');
 },

   formatCreatedAt(timestamp) {
     return moment(timestamp).format('DD/MM/YYYY HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
 },
 truncated(name) {
    const max = 40;
          if (name.length > max) {
              return name.substring(0, max) + '...';
          }
          return name;
      },

      go(link){

        window.location.href = link;
      },

  },
  components: {
    'app-header': Header,
    'app-footer': Footer,

    
  }
 
}
</script>

<style scoped>

button.action {
    border-radius: 0.5rem;
    border: none;
    border: 1px solid #9f9f9f;
    zoom: 121%;
    background-image: linear-gradient(0, #a00c0c, #c03d00);
    margin-right: 0.5rem;
    color: white;
    margin-left: 0.5rem;
}
button.action:hover {
    border-radius: 0.5rem;
    border: none;
    border: 1px solid #0233b9;
    zoom: 121%;
    background-color: #0080a7;
    margin-right: 0.5rem;
    color: rgb(255, 255, 255);
    margin-left: 0.5rem;
}
a.text-bold {
    font-weight: bold;
    /* padding: 2px; */
    color: #0063cd;
    zoom: 110%;
}


img.img-icon {
    width: 2rem;
    height: 2rem;
    border: 1px solid black;
}
body{
background-color:#005e8391;
}

.card-body {
    flex: 1 1 auto;
    padding: 1rem 1rem;
    height: 19rem;
}
.card {
    margin-bottom: 24px;
    box-shadow: 0 2px 3px #e4e8f0;
}
.card:hover {
    border:1px solid rgb(0, 207, 243);
    box-shadow: 0 2px 3px #070707;
}
.card {
    position: relative;
    display: flex;
    flex-direction: column;
    min-width: 0;
    word-wrap: break-word;
    background-color: #fff;
    background-clip: border-box;
    border: 1px solid #eff0f2;
    border-radius: 1rem;
}
.avatar-md {
    height: 4rem;
    width: 4rem;
}
.rounded-circle {
    border-radius: 50%!important;
}
.img-thumbnail {
    padding: 0.25rem;
    background-color: #f1f3f7;
    border: 1px solid #eff0f2;
    border-radius: 0.75rem;
}
.avatar-title {
    align-items: center;
    background-color: #3b76e1;
    color: #fff;
    display: flex;
    font-weight: 500;
    height: 100%;
    justify-content: center;
    width: 100%;
}
.bg-soft-primary {
    background-color: rgba(59,118,225,.25)!important;
}
a {
    text-decoration: none!important;
}
.badge-soft-danger {
    color: #f56e6e !important;
    background-color: rgba(245,110,110,.1);
}
.badge-soft-success {
    color: #e97c00 !important;
    background-color: rgba(99,173,111,.1);
}
.mb-0 {
    margin-bottom: 0!important;
}
.badge {
    display: inline-block;
    padding: 0.25em 0.6em;
    font-size: 75%;
    font-weight: 500;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: 0.75rem;
}



img.concac:hover{
    zoom:130%;
}

img.concac {
    padding: 1.5rem;
    margin-right: -2vw;
}

.text-white {
    color: #ffffff; 
}
.ui-bg-overlay-container, .ui-bg-video-container {
    position: relative;
}
.ui-bg-cover {
    background-color: transparent;
    background-position: center center;
    background-size: cover;
}
.ui-bg-overlay-container .ui-bg-overlay {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    display: block;
}

.bg-dark {
    background-image: linear-gradient(0, #00d0ff, #006fa7);

}
.opacity-50 {
    opacity: .5 !important;
}
.bg-dark {
    background-color: rgba(6, 9, 12, 0.9) !important;
}
.ui-bg-overlay-container>*, .ui-bg-video-container>* {
    position: relative;
}
@media (min-width: 992px){
    .container, .container-fluid {
        padding-right: 2rem;
        padding-left: 2rem;
    }
}
.media, .media>:not(.media-body), .jumbotron, .card {
    -ms-flex-negative: 1;
    flex-shrink: 1;
}
.d-flex, .d-inline-flex, .media, .media>:not(.media-body), .jumbotron, .card {
    -ms-flex-negative: 1;
    flex-shrink: 1;
}
.ui-w-100 {
    width: 100px !important;
    height: auto;
}
.font-weight-bold {
    font-weight: 700 !important;
}
.opacity-75 {
    opacity: .75 !important;
}
.tabs-alt.nav-tabs .nav-link.active, .tabs-alt.nav-tabs .nav-link.active:hover, .tabs-alt.nav-tabs .nav-link.active:focus, .tabs-alt>.nav-tabs .nav-link.active, .tabs-alt>.nav-tabs .nav-link.active:hover, .tabs-alt>.nav-tabs .nav-link.active:focus {
    -webkit-box-shadow: 0 -2px 0 #011f30 inset;
    box-shadow: 0 -2px 0 #6b1500 inset;
}

.nav-tabs:not(.nav-fill):not(.nav-justified) .nav-link, .nav-pills:not(.nav-fill):not(.nav-justified) .nav-link {
    margin-right: .125rem;
}
.nav-tabs.tabs-alt .nav-link, .tabs-alt>.nav-tabs .nav-link {
    border-width: 0 !important;
    border-radius: 0 !important;
    background-color: transparent !important;
}
.nav-tabs .nav-link.active {
    border-bottom-color: #fff;
}


li.nav-item {
    font-size: 1.5rem;
    font-weight: bold;

}
</style>