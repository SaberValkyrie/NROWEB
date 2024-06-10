package com.example.demo.controller;

import com.example.demo.dto.AccountResponse;
import com.example.demo.entity.Player;
import com.example.demo.support.Util;
import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.Account;
//import com.example.demo.service.CartService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
public class UserController {

    @Autowired
    private  UserService acccountService;


//=========================================LOGIN/LOGOUT==========================================================================

    @PostMapping("/login")
    public ResponseEntity<ResponseOpject> login(@RequestBody Account loginRequest) {
        Account account = acccountService.authenticate(loginRequest);
        if (account == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Sai tên đăng nhập hoặc mật khẩu", null);
        }
        if (account.token.length() <= 15){
            account.token = UUID.randomUUID().toString();
        }

        Player player = acccountService.getUserByAccount(account.id);
        acccountService.saveAccount(account);
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.token = account.token;
        accountResponse.code = account.code;

        if (player != null){
            accountResponse.player = player;
        }
        return Util.checkStatusRes(HttpStatus.OK, "Xác Minh Thành Công : " + account.username , accountResponse);
    }


//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    @PostMapping("/register")
    public ResponseEntity<ResponseOpject> register(@RequestBody Account loginRequest
    ,HttpServletRequest request) {

        String username = loginRequest.username;
        // Check if username contains any spaces
        if (username.contains(" ")) {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Tên tài khoản không được chứa dấu cách", null);
        }
        // Check if username contains any uppercase letters
        if (!username.equals(username.toLowerCase())) {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Tên tài khoản không được chứa chữ hoa", null);
        }

        // Check if username contains Vietnamese diacritical marks
        Pattern vietnameseDiacriticalMarksPattern = Pattern.compile("[àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]");
        if (vietnameseDiacriticalMarksPattern.matcher(username).find()) {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Tên tài khoản không được chứa dấu tiếng Việt", null);
        }

        Account acc = acccountService.checkUserName(loginRequest.username);
        if (acc != null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Tài Khoản Đã Tồn Tại", null);
        }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        Account account = new Account();
        account.username = loginRequest.username;
        account.password = Util.md5(loginRequest.password);
        account.ban = false;
        account.token = UUID.randomUUID().toString();
        account.ip = request.getRemoteAddr();
        account.sodu = 0;
        account.code = UUID.randomUUID().toString();
        account.is_admin = false;
        acccountService.saveAccount(account);
        //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



        return Util.checkStatusRes(HttpStatus.OK, "Đăng Ký Thành Công : ", null);
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @PostMapping("/logout/{token}")
    public ResponseEntity<ResponseOpject> logout(HttpServletRequest request,@PathVariable String token) {
        Account account = acccountService.getAccountBytoken(token);
        if (account == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tìm thấy account", null);
        }
        String res = UUID.randomUUID().toString();
        account.token = res.substring(1,10);
        acccountService.saveAccount(account);
        request.getSession().invalidate();
        return Util.checkStatusRes(HttpStatus.OK, "Đăng xuất thành công" + account.username, null);
    }
    @GetMapping("/getaccByToken/{token}")
    public ResponseEntity<ResponseOpject> getaccByToken(HttpServletRequest request,@PathVariable String token) {
        Account account = acccountService.getAccountBytoken(token);
        if (account == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tìm thấy account", null);
        }
        return Util.checkStatusRes(HttpStatus.OK, "ok", account);
    }


//@GetMapping("/checkAuthen/{token}/{code}")
//public ResponseEntity<ResponseOpject> check(@PathVariable String token,
//                                                    @PathVariable String code
//) {
//    User userToken = tokenService.getUserFromToken(token);
//    if (userToken == null) {
//        return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
//    }
//    if (!userToken.code.equals(code)){
//        return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Vui Lòng Đăng Nhập Lại", null);
//    }
//    return Util.checkStatusRes(HttpStatus.OK, "Get Account Success", userToken);
//}
//===================================================================================================================

    @PostMapping("/changePass/{token}/{codeXN}/{oldPass}")
    public ResponseEntity<ResponseOpject> changepass(
            @PathVariable String token,
             @PathVariable String codeXN,
            @PathVariable String oldPass,
            @RequestBody Account account
    ) {
//        User userToken = tokenService.getUserFromToken(token);
//        if (userToken == null) {
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
//        }
//        if (!userToken.code.equals(codeXN)){
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Vui lòng login lại", null);
//        }
//
//
//        Account accountToken = userService.getAccountByUser(userToken.username);
//
//        boolean pass = userService.matches(oldPass,accountToken.password);
//
//        if (!pass){
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Mật khẩu cũ không chính xác", null);
//        }
//
//        if (account.password.length() <= 6) {
//            return Util.checkStatus(HttpStatus.BAD_REQUEST, "Mật khẩu mới phải có ít nhất 7 ký tự", null);
//        }
//
//        accountToken.password = userService.mahoa(account.password);
//        userService.saveAccount(accountToken);
//
//        userToken.code = UUID.randomUUID().toString();
//        userService.saveUser(userToken);


        return Util.checkStatusRes(HttpStatus.OK, "Đổi Mật Khẩu Thành Công", null);
    }
}
