package com.mol.purchase.util.robot;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseArray;
import util.TimeUtil;
import util.UploadUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Cread_PDF
{
    public String Cread_PDF_function(String table_name, List<PurchaseArray> purchaseArrays, String[] page_text) //前台递交采购等表单生成PDF文件的方法
    {

        String URL= UploadUtils.getDirFile().getAbsolutePath()+"/documents/"+ TimeUtil.getNowOnlyDate()+"/" +table_name+".pdf";
        //开启一个线程
        Thread thread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    File file = new File(UploadUtils.getDirFile().getAbsolutePath()+"/documents/" + TimeUtil.getNowOnlyDate());
                    if(!file.exists())//判断这个路径是否存在
                    {
                    file.mkdirs();//不存在的情况下建立路径
                    }
                    //申请事由
                    String applyCause = page_text[0];
                    //采购详情
                    List<PurchaseArray> purchaseList =purchaseArrays;
                    Document document=new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(URL));//创建一个新的pdf文档传入一个生成路径
                    document.open();
                    Font FontChinese = new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), 24, Font.NORMAL);//设置中文编码和属性"UniGB-UCS2-H"
                    BaseFont bfChinese = BaseFont.createFont("C:/Windows/Fonts/simhei.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                    //字体
                    Font titleChinese = new Font(bfChinese, 20, Font.BOLD);
                    Font secondtitleChinese = new Font(bfChinese, 12, Font.NORMAL);
                    Font fontChinese_1 = new Font(bfChinese, 10, Font.NORMAL);
                    PdfPTable table = new PdfPTable(9);//定义表格列数
                    //// 设置表格宽度比例为%100
                    table.setWidthPercentage(100);
                    // 设置表格的宽度
                    table.setTotalWidth(580);
                    // 也可以每列分别设置宽度
                    table.setTotalWidth(new float[] {40,40,120, 70,100,90,40,40,40});
                    // 锁住宽度
                    table.setLockedWidth(true);
                    // 设置表格上面空白宽度
                    table.setSpacingBefore(10f);
                    // 设置表格下面空白宽度
                    table.setSpacingAfter(10f);
                    // 设置表格默认为无边框
                    table.getDefaultCell().setBorder(0);

                    //*********************************************采购类型******************************************************
                    table.addCell(pdfPCell(table_name,BaseColor.GRAY,titleChinese,50,9,true));

                    //********************************************申请事由**************************************************
                    table.addCell(pdfPCell(applyCause,BaseColor.WHITE,secondtitleChinese,40,5,false));

                    //********************************************日期**************************************************************
                    table.addCell(pdfPCell("日期："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) ,BaseColor.WHITE,fontChinese_1,50,4,true));
                    //*******************************************列头名称************************************************
                    String []title_name={"序号","物料ID","采购类型","采购品牌","采购名称","规格","数量","单位","历史均价"};//列头名称
                    for (int n=0;n<9;n++)//创建列标题
                    {
                        table.addCell(pdfPCell(title_name[n],BaseColor.GRAY,fontChinese_1,40,1,false));
                    }
                    //*************************************表单内容**************************************************
                    for (int n=0;n<purchaseList.size();n++)//遍历表单内容
                    {
                        PurchaseArray PA=purchaseList.get(n);
                        Class f=PA.getClass();
                        Field[]fields=f.getDeclaredFields();
                        for (int i=0;i<=fields.length;i++)//遍历实体类元素
                        {
                            if (i==0)
                            {
                                table.addCell(pdfPCell(String.valueOf(n+1),BaseColor.ORANGE,fontChinese_1,20,1,false));//序号
                            }
                            else
                            {
                                Field item;
                                item=fields[i-1];
                                item.setAccessible(true);
                                if (item.get(PA)==null)
                                {
                                    table.addCell( pdfPCell("暂无",BaseColor.ORANGE,fontChinese_1,50,1,false));//单个元素值
                                }
                                else
                                {
                                    table.addCell( pdfPCell(""+item.get(PA),BaseColor.ORANGE,fontChinese_1,35,1,false));//单个元素值
                                }
                            }
                        }
                    }
                    for (int i=0;i<page_text.length;i++)
                    {
                        if (i!=0 && i!=page_text.length-1)
                        {
                            table.addCell(pdfPCell(page_text[i],BaseColor.GRAY,secondtitleChinese,40,9,false));
                        }
                        else if (i==page_text.length-1)//备注
                        {
                            table.addCell(pdfPCell(page_text[i],BaseColor.ORANGE,secondtitleChinese,80,9,false));
                        }
                    }

                    document.add(table);//表单写入
                    //添加一页
                    //  document.newPage();
                    //  document.add(new Paragraph("New page"));
                    //图片
                    /*document.newPage();
                     Image img = Image.getInstance("C:/Users/41419/Desktop/2857b855cd13f58.jpg");
                     img.setAlignment(Image.LEFT | Image.TEXTWRAP);
                     img.setBorder(Image.BOX);
                     img.setBorderWidth(30);
                     img.setBorderColor(BaseColor.WHITE);
                     img.scaleToFit(30000, 3000);//大小
                     img.setRotationDegrees(0);//旋转
                     document.add(img);*/

                    document.close();
                }
                catch (Exception e)
                {
                 String jj="";
                }
            }
        });
        thread.start();//启动
        return URL;
    }

    private PdfPCell pdfPCell(String text,BaseColor baseColor,Font font,int row_height,int merge_colume,boolean Centered)
    {
        PdfPCell cell0 = new PdfPCell(new Paragraph(text,font));
        cell0.setBorderColor(BaseColor.BLACK);
        // 设置背景颜色
        cell0.setBackgroundColor(baseColor);
        // 设置跨两行
        cell0.setColspan(merge_colume);
        // 设置距左边的距离
        cell0.setPaddingLeft(10);
        cell0.setRowspan(2);
        // 设置高度
        cell0.setFixedHeight(row_height);
        // 设置垂直居中
        cell0.setVerticalAlignment(Element.ALIGN_MIDDLE);
        //设置水平居中
        if (Centered)
        {
            cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
        }
        return cell0;
    }
}
