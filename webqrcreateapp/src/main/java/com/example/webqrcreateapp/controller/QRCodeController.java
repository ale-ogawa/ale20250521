package com.example.webqrcreateapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.webqrcreateapp.service.QRCodeService;

@Controller
public class QRCodeController {

    // static/imageフォルダのパスを定数で管理
    private static final String IMAGE_DIR = "src/main/resources/static/image/";

    // QRコード生成サービスをDI（依存性注入）で取得
    @Autowired
    private QRCodeService qrCodeService;

    /**
     * URLパラメータを受け取りQRコード画像を生成し、ビューへファイルパスを渡す
     *
     * @param url QRコード化したいURL文字列
     * @param model Thymeleafビューにデータを渡すためのModel
     * @return 表示させるテンプレート名（qrview.html）
     * @throws Exception 画像生成処理で例外が発生した場合
     */
    @GetMapping("/showQRCode")
    public String showQRCode(@RequestParam("url") String url, Model model) throws Exception {
        String fileName = "qr_test.png";               // 保存するファイル名
        String fullPath = IMAGE_DIR + fileName;         // フルパスを生成

        // QRコード画像を作成し、static/imageフォルダに保存する
        qrCodeService.generateQRCodeImageToFile(url, 300, 300, fullPath);

        // ビューに画像のアクセスパスをセット（ブラウザからは /image/qr_test.png でアクセス可能）
        model.addAttribute("qrImagePath", "/image/" + fileName);

        // qrview.htmlを返す（テンプレートで画像を表示）
        return "qrview";
    }
}