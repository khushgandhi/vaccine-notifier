package com.vaccine.notifier.vaccinenotifier.config;

import com.vaccine.notifier.vaccinenotifier.model.Subscriber;

public class MailTemplate {
  
	public final static String WELCOME_SUBSCRIBER = "<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
			+ "   <tbody>\r\n"
			+ "      <tr>\r\n"
			+ "         <td align=\"center\">\r\n"
			+ "            <table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
			+ "               <tbody>\r\n"
			+ "                  <tr>\r\n"
			+ "                     <td align=\"center\" valign=\"top\"\r\n"
			+ "                     style=\"background: url(https://static.pib.gov.in/WriteReadData/userfiles/image/image008TG7T.jpg)no-repeat center center ;background-size:cover; background-position:top;\"\r\n"
			+ "                     400\"\"=\"\">\r\n"
			+ "                     <table class=\"col-600\" width=\"600\" height=\"400\" border=\"0\" align=\"center\"\r\n"
			+ "                        cellpadding=\"0\" cellspacing=\"0\">\r\n"
			+ "                        <tbody>\r\n"
			+ "                           <tr>\r\n"
			+ "                              <td height=\"40\"></td>\r\n"
			+ "                           </tr>\r\n"
			+ "                           <tr>\r\n"
			+ "                              <td height=\"50\"></td>\r\n"
			+ "                           </tr>\r\n"
			+ "                        </tbody>\r\n"
			+ "                     </table>\r\n"
			+ "                     </td>\r\n"
			+ "                  </tr>\r\n"
			+ "               </tbody>\r\n"
			+ "            </table>\r\n"
			+ "         </td>\r\n"
			+ "      </tr>\r\n"
			+ "      <tr>\r\n"
			+ "         <td align=\"center\">\r\n"
			+ "            <table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\r\n"
			+ "               style=\"margin-left:20px; margin-right:20px; border-left: 1px solid #dbd9d9; border-right: 1px solid #dbd9d9;\">\r\n"
			+ "               <tbody>\r\n"
			+ "                  <tr>\r\n"
			+ "                     <td height=\"35\"></td>\r\n"
			+ "                  </tr>\r\n"
			+ "                  <tr>\r\n"
			+ "                     <td align=\"center\"\r\n"
			+ "                        style=\"font-family: 'Raleway', sans-serif; font-size:22px; font-weight: bold; color:#2a3a4b;\">\r\n"
			+ "                        Hey COVID Warrior thanks for subscribing us!!\r\n"
			+ "                     </td>\r\n"
			+ "                  </tr>\r\n"
			+ "                  <tr>\r\n"
			+ "                     <td height=\"10\"></td>\r\n"
			+ "                  </tr>\r\n"
			+ "                  <tr>\r\n"
			+ "                     <td align=\"center\"\r\n"
			+ "                        style=\"font-family: 'Lato', sans-serif; font-size:14px; color:#5a5858; line-height:24px; font-weight: 300;\">\r\n"
			+ "                        We'll notify you as soon as some vaccination center gets available near you on daily\r\n"
			+ "                        basis,<br />\r\n"
			+ "                        And you can also check all the available slots on our website.<br />\r\n"
			+ "                        <a href=\"www.vaccinenotifier.in\">www.vaccinenotifier.in</a>\r\n"
			+ "                     </td>\r\n"
			+ "                  </tr>\r\n"
			+ "               </tbody>\r\n"
			+ "            </table>\r\n"
			+ "         </td>\r\n"
			+ "      </tr>\r\n"
			+ "      <tr>\r\n"
			+ "         <td align=\"center\">\r\n"
			+ "            <table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\r\n"
			+ "               style=\"margin-left:20px; margin-right:20px;\">\r\n"
			+ "               <tbody>\r\n"
			+ "                  <tr>\r\n"
			+ "                     <td align=\"center\">\r\n"
			+ "                        <table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\r\n"
			+ "                           style=\" border-left: 1px solid #dbd9d9; border-right: 1px solid #dbd9d9;\">\r\n"
			+ "                           <tbody>\r\n"
			+ "                              <tr>\r\n"
			+ "                                 <td height=\"50\"></td>\r\n"
			+ "                              </tr>\r\n"
			+ "                              <tr>\r\n"
			+ "                                 <td align=\"center\" bgcolor=\"#34495e\">\r\n"
			+ "                                    <table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\"\r\n"
			+ "                                       cellspacing=\"0\">\r\n"
			+ "                                       <tbody>\r\n"
			+ "                                          <tr>\r\n"
			+ "                                             <td height=\"20\"></td>\r\n"
			+ "                                          </tr>\r\n"
			+ "                                          <tr>\r\n"
			+ "                                             <td align=\"center\"\r\n"
			+ "                                                style=\"font-family: 'Raleway', sans-serif; font-size:20px; color:#f1c40f; line-height:24px; font-weight: bold;\">\r\n"
			+ "                                                <a href=\"www.vaccinenotifier.in\" style=\"color:#f1c40f\">Want to Unsubscribe?</a>\r\n"
			+ "                                             </td>\r\n"
			+ "                                          </tr>\r\n"
			+ "                                          <tr>\r\n"
			+ "                                             <td height=\"20\"></td>\r\n"
			+ "                                          </tr>\r\n"
			+ "                                          <!-- <tr>\r\n"
			+ "                                             <td height=\"40\"></td>\r\n"
			+ "                                             </tr> -->\r\n"
			+ "                                       </tbody>\r\n"
			+ "                                    </table>\r\n"
			+ "                                 </td>\r\n"
			+ "                              </tr>\r\n"
			+ "                           </tbody>\r\n"
			+ "                        </table>\r\n"
			+ "                     </td>\r\n"
			+ "                  </tr>\r\n"
			+ "               </tbody>\r\n"
			+ "            </table>\r\n"
			+ "         </td>\r\n"
			+ "      </tr>\r\n"
			+ "   </tbody>\r\n"
			+ "</table>";
	
	
	public static String getSlotsAvailableMsg(int centerCount,String url)
	{
		String s ="<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
				+ "   <tbody>\r\n"
				+ "      <tr>\r\n"
				+ "         <td align=\"center\">\r\n"
				+ "            <table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
				+ "               <tbody>\r\n"
				+ "                  <tr>\r\n"
				+ "                     <td align=\"center\" valign=\"top\"\r\n"
				+ "                      style=\"background: url(https://static.pib.gov.in/WriteReadData/userfiles/image/image008TG7T.jpg)no-repeat center center ;background-size:cover; background-position:top;\"\r\n"
				+ "                     400\"\"=\"\">\r\n"
				+ "                     <table class=\"col-600\" width=\"600\" height=\"400\" border=\"0\" align=\"center\"\r\n"
				+ "                        cellpadding=\"0\" cellspacing=\"0\">\r\n"
				+ "                        <tbody>\r\n"
				+ "                           <tr>\r\n"
				+ "                              <td height=\"40\"></td>\r\n"
				+ "                           </tr>\r\n"
				+ "                           <tr>\r\n"
				+ "                              <td height=\"50\"></td>\r\n"
				+ "                           </tr>\r\n"
				+ "                        </tbody>\r\n"
				+ "                     </table>\r\n"
				+ "                     </td>\r\n"
				+ "                  </tr>\r\n"
				+ "               </tbody>\r\n"
				+ "            </table>\r\n"
				+ "         </td>\r\n"
				+ "      </tr>\r\n"
				+ "      <tr>\r\n"
				+ "         <td align=\"center\">\r\n"
				+ "            <table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-left:20px; margin-right:20px; border-left: 1px solid #dbd9d9; border-right: 1px solid #dbd9d9;\">\r\n"
				+ "               <tbody>\r\n"
				+ "                  <tr>\r\n"
				+ "                     <td height=\"35\"></td>\r\n"
				+ "                  </tr>\r\n"
				+ "                  <tr>\r\n"
				+ "                     <td align=\"center\" style=\"font-family: 'Raleway', sans-serif; font-size:22px; font-weight: bold; color:#2a3a4b;\">Hey Covid Warrior<br/> We found some available slots near you !!</td>\r\n"
				+ "                  </tr>\r\n"
				+ "                  <tr>\r\n"
				+ "                     <td height=\"10\"></td>\r\n"
				+ "                  </tr>\r\n"
				+ "                  <tr>\r\n"
				+ "                     <td align=\"center\" style=\"font-family: 'Lato', sans-serif; font-size:14px; color:#757575; line-height:24px; font-weight: 300;\">\r\n"
				+ "                        Please click on below link to see all the available slots near you.<br>\r\n"
				+ "                        <a href=\"+"+url+"\">www.vaccinenotifier.in</a>\r\n"
				+ "                     </td>\r\n"
				+ "                  </tr>\r\n"
				+ "               </tbody>\r\n"
				+ "            </table>\r\n"
				+ "         </td>\r\n"
				+ "      </tr>\r\n"
				+ "      <tr>\r\n"
				+ "         <td align=\"center\">\r\n"
				+ "            <table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\r\n"
				+ "               style=\"margin-left:20px; margin-right:20px;\">\r\n"
				+ "               <tbody>\r\n"
				+ "                  <tr>\r\n"
				+ "                     <td height=\"5\"></td>\r\n"
				+ "                  </tr>\r\n"
				+ "                  <tr>\r\n"
				+ "                     <td align=\"center\">\r\n"
				+ "                        <table align=\"center\" style=\"border:1px solid #e2e2e2; width:100%\" class=\"col2\" width=\"287\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
				+ "                           <tbody>\r\n"
				+ "                              <tr>\r\n"
				+ "                                 <td height=\"40\"  align=\"center\" bgcolor=\"#2b3c4d\" style=\"font-family: 'Raleway', sans-serif; font-size:18px; color:#f1c40f; line-height:30px; font-weight: bold;\">Total Available Centers</td>\r\n"
				+ "                              </tr>\r\n"
				+ "                              <tr>\r\n"
				+ "                                 <td align=\"center\">\r\n"
				+ "                                    <table class=\"insider\" width=\"237\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
				+ "                                       <tbody>\r\n"
				+ "                                          <tr>\r\n"
				+ "                                             <td height=\"20\"></td>\r\n"
				+ "                                          </tr>\r\n"
				+ "                                          <tr align=\"center\" style=\"line-height:0px;\">\r\n"
				+ "                                             <td style=\"font-family: 'Lato', sans-serif; font-size:48px; color:#2b3c4d; font-weight: bold; line-height: 44px;\">"+centerCount+"</td>\r\n"
				+ "                                          </tr>\r\n"
				+ "                                          <tr>\r\n"
				+ "                                             <td height=\"15\"></td>\r\n"
				+ "                                          </tr>\r\n"
				+ "                                          <tr>\r\n"
				+ "                                             <td align=\"center\">\r\n"
				+ "                                                <table width=\"145\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border: 2px solid #2b3c4d;\">\r\n"
				+ "                                                   <tbody>\r\n"
				+ "                                                      <tr>\r\n"
				+ "                                                         <td width=\"10\"></td>\r\n"
				+ "                                                         <td height=\"30\" align=\"center\" style=\"font-family: 'Lato', sans-serif; font-size:14px; font-weight: 300; color:#2b3c4d;\">\r\n"
				+ "                                                            <a href=\"#\" style=\"color: #2b3c4d;\">Show Centers</a>\r\n"
				+ "                                                         </td>\r\n"
				+ "                                                         <td width=\"10\"></td>\r\n"
				+ "                                                      </tr>\r\n"
				+ "                                                   </tbody>\r\n"
				+ "                                                </table>\r\n"
				+ "                                             </td>\r\n"
				+ "                                          </tr>\r\n"
				+ "                                       </tbody>\r\n"
				+ "                                    </table>\r\n"
				+ "                                 </td>\r\n"
				+ "                              </tr>\r\n"
				+ "                           </tbody>\r\n"
				+ "                        </table>\r\n"
				+ "                     </td>\r\n"
				+ "                  </tr>\r\n"
				+ "               </tbody>\r\n"
				+ "            </table>\r\n"
				+ "         </td>\r\n"
				+ "      </tr>\r\n"
				+ "      <tr>\r\n"
				+ "         <td align=\"center\">\r\n"
				+ "            <table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\r\n"
				+ "               style=\"margin-left:20px; margin-right:20px;\">\r\n"
				+ "               <tbody>\r\n"
				+ "                  <tr>\r\n"
				+ "                     <td align=\"center\">\r\n"
				+ "                        <table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\r\n"
				+ "                           style=\" border-left: 1px solid #dbd9d9; border-right: 1px solid #dbd9d9;\">\r\n"
				+ "                           <tbody>\r\n"
				+ "                              <tr>\r\n"
				+ "                                 <td height=\"5\"></td>\r\n"
				+ "                              </tr>\r\n"
				+ "                              <tr>\r\n"
				+ "                                 <td align=\"center\" bgcolor=\"#34495e\">\r\n"
				+ "                                    <table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\"\r\n"
				+ "                                       cellspacing=\"0\">\r\n"
				+ "                                       <tbody>\r\n"
				+ "                                          <tr>\r\n"
				+ "                                             <td height=\"20\"></td>\r\n"
				+ "                                          </tr>\r\n"
				+ "                                          <tr>\r\n"
				+ "                                             <td align=\"center\"\r\n"
				+ "                                                style=\"font-family: 'Raleway', sans-serif; font-size:20px; color:#f1c40f; line-height:24px; font-weight: bold;\">\r\n"
				+ "                                                <a href=\"www.vaccinenotifier.in\" style=\"color:#f1c40f\">Want to Unsubscribe?</a>\r\n"
				+ "                                             </td>\r\n"
				+ "                                          </tr>\r\n"
				+ "                                          <tr>\r\n"
				+ "                                             <td height=\"20\"></td>\r\n"
				+ "                                          </tr>\r\n"
				+ "                                       </tbody>\r\n"
				+ "                                    </table>\r\n"
				+ "                                 </td>\r\n"
				+ "                              </tr>\r\n"
				+ "                           </tbody>\r\n"
				+ "                        </table>\r\n"
				+ "                     </td>\r\n"
				+ "                  </tr>\r\n"
				+ "               </tbody>\r\n"
				+ "            </table>\r\n"
				+ "         </td>\r\n"
				+ "      </tr>\r\n"
				+ "   </tbody>\r\n"
				+ "</table>";
		return s;
	}
}
