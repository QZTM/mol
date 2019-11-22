package com.mol.ddmanage.Service.Office;

import com.mol.ddmanage.Ben.Office.ReviewPeolpesben;
import com.mol.ddmanage.mapper.Office.ReviewBargainingHistoryPageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ReviewBargainingHistoryPageService
{
    @Resource
    ReviewBargainingHistoryPageMapper title_details_mapper;

    public Map Purchase(String purchase_id)
    {
        return title_details_mapper.Purchase(purchase_id);
    }

    public ArrayList<ReviewPeolpesben> ReviewPeolpes(String purchase_id,String CorpId)
    {
        ArrayList<ReviewPeolpesben> reviewPeolpesbens=new ArrayList<>();

        ArrayList <Map> ReviewStatus=title_details_mapper.PurchaseReviewStatus(purchase_id);//获取订单审核状态
        for (int n=0;n<ReviewStatus.size();n++)//遍历操作节点
        {
            if (ReviewStatus.get(n).get("ACT_NAME_").toString().equals("审核通过"))
            {
                for(int n_1=0;n_1<ReviewStatus.size();n_1++)//查看有多少审批步骤
                {
                    for (int n_2=0;n_2<ReviewStatus.size();n_2++)//逐步遍历审批人
                    {
                      if (ReviewStatus.get(n_2).get("ACT_NAME_").toString().equals("审核人"+(n_1+1)))//有这个审核人
                      {
                          ReviewPeolpesben reviewPeolpesben=new ReviewPeolpesben();
                          if (ReviewStatus.get(n_2).get("UserName")!=null)//名字不为空
                          {
                              reviewPeolpesben.setName(ReviewStatus.get(n_2).get("UserName").toString());//审批人姓名
                          }
                          else
                          {
                              reviewPeolpesben.setName("姓名未知");//审批人姓名
                          }

                          if (ReviewStatus.get(n_2).get("Remarks")!=null)//备注不为空
                          {
                              reviewPeolpesben.setRemarks(ReviewStatus.get(n_2).get("Remarks").toString());//备注
                          }
                          reviewPeolpesben.setTime(ReviewStatus.get(n_2).get("END_TIME_").toString());//审批时间
                          reviewPeolpesben.setApprovalStatus("1");//审批通过
                          reviewPeolpesbens.add(reviewPeolpesben);
                          break;
                      }
                    }
                }
                break;
            }
            else if (ReviewStatus.get(n).get("ACT_NAME_").toString().equals("审核未通过"))
            {
                for(int n_1=0;n_1<ReviewStatus.size();n_1++)//查看有多少审批步骤
                {
                    for (int n_2=0;n_2<ReviewStatus.size();n_2++)//逐步遍历审批人
                    {
                        if (ReviewStatus.get(n_2).get("ACT_NAME_").toString().equals("审核人"+(n_1+1)))//有这个审核人
                        {
                            ReviewPeolpesben reviewPeolpesben=new ReviewPeolpesben();
                            if (ReviewStatus.get(n_2).get("UserName")!=null)//如果查出来的姓名不为空
                            {
                                reviewPeolpesben.setName(ReviewStatus.get(n_2).get("UserName").toString());//审批人姓名
                            }
                            else
                            {
                                reviewPeolpesben.setName("姓名未知");//审批人姓名
                            }

                            if (ReviewStatus.get(n_2).get("Remarks")!=null)//备注不为空
                            {
                                reviewPeolpesben.setRemarks(ReviewStatus.get(n_2).get("Remarks").toString());//备注
                            }
                            reviewPeolpesben.setTime(ReviewStatus.get(n_2).get("END_TIME_").toString());//审批时间

                            reviewPeolpesben.setApprovalStatus("1");//审批通过
                            reviewPeolpesbens.add(reviewPeolpesben);
                            break;
                        }
                    }
                }
                if (reviewPeolpesbens.size()!=0)
                {
                    reviewPeolpesbens.get(reviewPeolpesbens.size()-1).setApprovalStatus("2");//最后一个审批人一定是拒绝的
                }
                break;
            }
            else if (n+1==ReviewStatus.size())//最后一次循环依然没有断定是拒绝还是通过表明正在进行中
            {
                for(int n_1=0;n_1<ReviewStatus.size();n_1++)//查看有多少审批步骤
                {
                    for (int n_2=0;n_2<ReviewStatus.size();n_2++)//逐步遍历审批人
                    {
                        String name1=ReviewStatus.get(n_2).get("ACT_NAME_").toString();
                        String name2="审核人"+(n_1+1);
                        if (ReviewStatus.get(n_2).get("ACT_NAME_").toString().equals("审核人"+(n_1+1)))//有这个审核人
                        {
                            ReviewPeolpesben reviewPeolpesben=new ReviewPeolpesben();
                            if (ReviewStatus.get(n_2).get("UserName")!=null)
                            {
                                reviewPeolpesben.setName(ReviewStatus.get(n_2).get("UserName").toString());//审批人姓名
                            }
                            else
                            {
                                reviewPeolpesben.setName("姓名未知");//审批人姓名
                            }

                            if (ReviewStatus.get(n_2).get("Remarks")!=null)//备注不为空
                            {
                                reviewPeolpesben.setRemarks(ReviewStatus.get(n_2).get("Remarks").toString());//备注
                            }
                            if (ReviewStatus.get(n_2).get("END_TIME_")!=null)
                            {
                                reviewPeolpesben.setTime(ReviewStatus.get(n_2).get("END_TIME_").toString());//审批时间
                            }

                            reviewPeolpesben.setApprovalStatus("1");//审批通过
                            reviewPeolpesbens.add(reviewPeolpesben);
                            break;
                        }
                    }
                }
                if (reviewPeolpesbens.size()!=0)
                {
                    reviewPeolpesbens.get(reviewPeolpesbens.size()-1).setApprovalStatus("0");//最后一个审批人正在审批
                }
                break;
            }
        }

        return reviewPeolpesbens;
    }

}
