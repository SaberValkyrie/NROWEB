<template>
    <link rel="stylesheet" href="https://allyoucan.cloud/cdn/icofont/1.0.1/icofont.css" integrity="sha384-jbCTJB16Q17718YM9U22iJkhuGbS0Gd2LjaWb4YJEZToOPmnKDjySVa323U+W7Fv" crossorigin="anonymous">
    <app-header></app-header>
    <br>    <br>    <br>    
<div class="container" >


    <div class="gift" v-if="!isSell">
  <input type="text" class="form-control" v-model="code" placeholder="Nhập GIFTCODE" />
  <button class="apdung" @click="apdung">➔</button>
</div>



    <div class="row" v-if="!isSell">

        <div class="col-md-9">
            <div class="osahan-account-page-right shadow-sm bg-white p-4 h-100">
                <div class="tab-content" id="myTabContent">

                    <div class="tab-pane fade  active show" id="favourites" role="tabpanel" aria-labelledby="favourites-tab">
                        <div class="row">
                          <h1 class="text-danger" v-if="ListItems.length == 0">Bạn không có Beyblade nào trong kho</h1>

                            <div class="col-md-4 col-sm-6 mb-4 pb-2" v-for="item in ListItems" :key="item.id">
    <div :class="item.beyBlade.isBoss && item.vinhvien? 'boss list-card bg-white h-100 rounded overflow-hidden position-relative' :'list-card bg-white h-100 rounded overflow-hidden position-relative shadow-sm'">
      <div class="list-card-image">
        <div class="favourite-heart text-danger position-absolute">
          <a href="#"><i class="icofont-heart"></i></a>
        </div>
        
        <div class="member-plan position-absolute">
            <div class="home-product-item__rating">
             
             <i v-if="(item.beyBlade.power + item.beyBlade.hp) >= 50000">⭐</i>
             <i v-if="(item.beyBlade.power + item.beyBlade.hp) >= 150000">⭐</i>
             <i v-if="(item.beyBlade.power + item.beyBlade.hp) >= 350000">⭐</i>
             <i v-if="(item.beyBlade.power + item.beyBlade.hp) >= 450000">⭐</i>
             <i v-if="(item.beyBlade.power + item.beyBlade.hp) >= 650000">⭐</i>
             <i v-if="(item.beyBlade.power + item.beyBlade.hp) >= 1000000">⭐</i>

             </div>
          
        </div>
        
        <a href="#">
          <img :src="item.beyBlade.images" :class="item.beyBlade.isBoss ? 'img-fluid item-img' : 'img-fluid item-img'">
        </a>
      </div>
      <div class="p-3 position-relative">
        <div class="list-card-body">
          <p class="text-gray mb-3">{{ truncated(item.beyBlade.name) }}</p>
          
          <p v-if="!item.vinhvien" class="text-danger mb-3">
            Ngày Hết Hạn:{{ formatCreatedAt(item.ngayhethan) }}
          </p>
          <p v-else class="text-success mb-3">Vĩnh Viễn</p>
        
          <div v-if="!item.selectedBey">
            
            <button @click="setBey(item)"   class="btn btn-primary" 
            type="button">  Chọn  <span class="spinner-grow spinner-green-sm" role="status" aria-hidden="true"> </span>
            </button>
<br><br>
            <button  @click="sellItem(item)"  class="btn btn-primary">Bán</button>

            

          </div>
          <div v-else>
            <button class="btn btn-primary" type="button" disabled="">Đang Sử Dụng</button>
          </div>
       

          
        </div>
      </div>
    </div>
  </div>
                           
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container" v-else>
    <br>
    <div class="row">
        <div class="col-lg-5 col-md-7 mx-auto my-auto">
            <div class="card">
                <div class="card-body px-lg-5 py-lg-5 text-center">
                    <img :src="sellBey.beyBlade.images" class="rounded-circle avatar-lg img-thumbnail mb-4" alt="profile-image">
                    <h2 class="text-info">Xác Nhận Bán {{ sellBey.beyBlade.name }}</h2>
                    <p class="mb-4">thu về ít nhất {{ convert(sellBey.beyBlade.price / 5)  }} Beypoint</p>
                  
                    <div class="text-center">
                      <button type="button" @click="cancel()" style="    color: white;
    margin-left: 1vw;
    margin-right: 1vw;" class="btn bg-info btn-lg my-4">Hủy Bỏ</button>

                            <button type="button" style="color: white;" @click="sell()" class="btn bg-info btn-lg my-4">Bán Thân</button>
                        </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
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

import { GameService } from '@/core/service/game';

export default {
  name: 'Profile',
  computed: {
   ...mapGetters(['loggedInUser']),
 },
created() {
 this.getInfo();
 this.loadUser();
 this.getItemBag();
 },
 data() {
   return {
     baseUrl : baseURL,
     token: localStorage.getItem('token'),
   
     accountService: new AccountService(),
     gameService: new GameService(),
     userInfo: {},
     ListItems:[],
     selected:{},
     code:'',
     sellBey:{},
     isSell:false,

   };
 },

 methods: {
  sell(){
    this.gameService.sell(this.token,this.sellBey).then(res => {
        toast.success(res.data.message)
        this.cancel()
        this.getItemBag()
        }).catch(error => {
 toast.warning(error.response.data.message)
});
  },
  convert(power) {
const formatter = new Intl.NumberFormat('vi-VN', { maximumFractionDigits: 1 });
if (power >= 1e9) {
  return formatter.format(power / 1e9) + ' Tỷ';
} else if (power >= 1e6) {
  return formatter.format(power / 1e6) + ' Tr';
} else if (power >= 1e3) {
  return formatter.format(power / 1e3) + ' K';
} else {
  return formatter.format(power);
}
},
  sellItem(item){
    this.sellBey = item;
    this.isSell = true;
  },
  cancel(){
    this.isSell = false;
  },

    apdung(){
        this.gameService.checkGC(this.token,this.code).then(res => {
        toast.success(res.data.message)
        this.getItemBag()
        }).catch(error => {
 toast.warning(error.response.data.message)
});
    },
 

setBey(bey){
    this.selected = bey;
    this.gameService.setItem(this.token,bey).then(res => {
        toast.success(res.data.message)
        this.getItemBag()
        }).catch(error => {
 toast.warning(error.response.data.message)
});
},

    getItemBag(){
        this.gameService.getItems(this.token).then(res => {
            this.ListItems = res.data.data.items;
        })
    },

   async getInfo() {
 try {
   this.userInfo = await this.accountService.getUserInfo(this.token);
   
 } catch (error) {
   toast.success('Hãy cập nhật thông tin của bạn');
   window.location.href ='/';
   console.error('Error fetching:', error);
 }
},
truncated(name) {
    const max = 18;
          if (name.length > max) {
              return name.substring(0, max) + '...';
          }
          return name;
      },


loadUser(){
  this.accountService.getUserLogin(this.token).then(res => {

  })
},

  formatBirthDate(date) {
   if (!date) return ''; // Đảm bảo xử lý trường hợp ngày sinh không tồn tại
   return moment(date).format('YYYY-MM-DD');
 },
   formatCreatedAt(timestamp) {
     return moment(timestamp).format('DD/MM/YYYY HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
 },


  },
  components: {
    'app-header': Header,
    'app-footer': Footer,

    HelloWorld
  }
 
}
</script>













<style>

body{
    margin-top:20px;
    background:#eee;
}
/* My Account */
.payments-item img.mr-3 {
    width: 47px;
}
.order-list .btn {
    border-radius: 2px;
    min-width: 121px;
    font-size: 13px;
    padding: 7px 0 7px 0;
}
.osahan-account-page-left .nav-link {
    padding: 18px 20px;
    border: none;
    font-weight: 600;
    color: #535665;
}
.osahan-account-page-left .nav-link i {
    width: 28px;
    height: 28px;
    background: #535665;
    display: inline-block;
    text-align: center;
    line-height: 29px;
    font-size: 15px;
    border-radius: 50px;
    margin: 0 7px 0 0px;
    color: #fff;
}
.osahan-account-page-left .nav-link.active {
    background: #f3f7f8;
    color: #282c3f !important;
}
.osahan-account-page-left .nav-link.active i {
    background: #282c3f !important;
}
.osahan-user-media img {
    width: 6vw;
}
.card offer-card h5.card-title {
    border: 2px dotted #000;
}
.card.offer-card h5 {
    border: 1px dotted #daceb7;
    display: inline-table;
    color: #17a2b8;
    margin: 0 0 19px 0;
    font-size: 15px;
    padding: 6px 10px 6px 6px;
    border-radius: 2px;
    background: #fffae6;
    position: relative;
}
.card.offer-card h5 img {
    height: 22px;
    object-fit: cover;
    width: 22px;
    margin: 0 8px 0 0;
    border-radius: 2px;
}
.card.offer-card h5:after {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-bottom: 4px solid #daceb7;
    content: "";
    left: 30px;
    position: absolute;
    bottom: 0;
}
.card.offer-card h5:before {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-top: 4px solid #daceb7;
    content: "";
    left: 30px;
    position: absolute;
    top: 0;
}
.payments-item .media {
    align-items: center;
}
.payments-item .media img {
    margin: 0 40px 0 11px !important;
}
.reviews-members .media .mr-3 {
    width: 56px;
    height: 56px;
    object-fit: cover;
}
.order-list img.mr-4 {
    width: 70px;
    height: 70px;
    object-fit: cover;
    box-shadow: 0 .125rem .25rem rgba(0, 0, 0, .075)!important;
    border-radius: 2px;
}
.osahan-cart-item p.text-gray.float-right {
    margin: 3px 0 0 0;
    font-size: 12px;
}
.osahan-cart-item .food-item {
    vertical-align: bottom;
}

.h1, .h2, .h3, .h4, .h5, .h6, h1, h2, h3, h4, h5, h6 {
    color: #000000;
}

.shadow-sm {
    box-shadow: 0 .125rem .25rem rgba(0,0,0,.075)!important;
}

.rounded-pill {
    border-radius: 50rem!important;
}
a:hover{
    text-decoration:none;
}
p {
    font-size: 1.5em;
    color: #000000;
}
.col-md-3{
    zoom: 150%;
}


.badge-dark {
    color: #fff;
    zoom: 200%;
    background-color: #8f0000;
    border: 1px solid #fff;
}


.activeTrue{
    color: #fff;
    background-color: #8f0000;
    border: 1px solid #fff;
}




.img-fluid {
    padding: 1vw;
    width: 20vw;
    height: 20vw;
}


.col-md-4.col-sm-6.mb-4.pb-2:hover {
    box-shadow: 0 1px 2px #080808;
}

.row {
    width: 87vw;
    display: -ms-flexbox;
    display: flex;
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;
    margin-right: -15px;
    margin-left: -15px;
}


.apdung {
      border: none;
    background-color:black;
 
}
.gift {
    display: flex;
    align-items: center;
    width: 32vw;
    zoom: 150%;
}

.form-control {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-right: 10px;
  font-size: 16px;
}

.apdung {
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  color: white;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease;
 
    margin-left: -2vw;

}

.apdung:hover {
  background-color: #0056b3;
}
@keyframes glowing-border {
  1% {
    border-color: #ac2a02;
    box-shadow: 0 0 5px #8f1100;
  }
  50% {
    border-color: #3400c4;
    box-shadow: 0 0 20px #242c02;
  }
  100% {
    border-color: #6b6035;
    box-shadow: 0 0 5px #31018b;
  }
}
.boss {
    padding: 0vw;
    border: 3px solid;
    /* border-radius: 50%; */
    animation: glowing-border 3s infinite;
}

.list-card-image {
  position: relative;
  height: 20vw;
}

.member-plan.position-absolute {
    width: 2px;
}

</style>