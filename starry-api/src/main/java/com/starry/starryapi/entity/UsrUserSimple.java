package com.starry.starryapi.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UsrUserSimple implements Serializable {
   private String UserID;
   private String UserLoginID;
   private String UserName;
   private String WorkNumber;
   private String UserAlias;
   private String FullPathCode;
   private String FullPathText;
   private String Grade;
   private String CompanyName;
   private String OrganizationID;
   private String OrganizationName;
   private String Corporates;
   private String PublicField;
   private String MobilePhone;
   private String PASSWORD;
   private String UserType;
   private String JobTitle;
   private String BudgetOrgCode;
   private String IsPublicAccounts;
   private String BankAccount;
   private String BankName;
   private String SourceType;
   private String DeptOrgCode;
}
