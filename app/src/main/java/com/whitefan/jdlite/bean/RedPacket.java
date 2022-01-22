package com.whitefan.jdlite.bean;

import java.math.BigDecimal;
import java.util.List;

public class RedPacket {


    private Data data;
    private int errcode;
    private String msg;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class Data {
        private int avaiCount;
        private String balance;
        private int countdownTime;
        private String expiredBalance;
        private int serverCurrTime;
        private UseRedInfo useRedInfo;

        public int getAvaiCount() {
            return avaiCount;
        }

        public void setAvaiCount(int avaiCount) {
            this.avaiCount = avaiCount;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public int getCountdownTime() {
            return countdownTime;
        }

        public void setCountdownTime(int countdownTime) {
            this.countdownTime = countdownTime;
        }

        public String getExpiredBalance() {
            return expiredBalance;
        }

        public void setExpiredBalance(String expiredBalance) {
            this.expiredBalance = expiredBalance;
        }

        public int getServerCurrTime() {
            return serverCurrTime;
        }

        public void setServerCurrTime(int serverCurrTime) {
            this.serverCurrTime = serverCurrTime;
        }

        public UseRedInfo getUseRedInfo() {
            return useRedInfo;
        }

        public void setUseRedInfo(UseRedInfo useRedInfo) {
            this.useRedInfo = useRedInfo;
        }

        public static class UseRedInfo {
            private int count;
            private List<RedList> redList;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<RedList> getRedList() {
                return redList;
            }

            public void setRedList(List<RedList> redList) {
                this.redList = redList;
            }

            public static class RedList {
                private String activityName;
                private BigDecimal balance;
                private int beginTime;
                private String delayRemark;
                private String discount;
                private long endTime;
                private String hbId;
                private int hbState;
                private boolean isDelay;
                private String orgLimitStr;

                public String getActivityName() {
                    return activityName;
                }

                public void setActivityName(String activityName) {
                    this.activityName = activityName;
                }

                public BigDecimal getBalance() {
                    return balance;
                }

                public void setBalance(BigDecimal balance) {
                    this.balance = balance;
                }

                public int getBeginTime() {
                    return beginTime;
                }

                public void setBeginTime(int beginTime) {
                    this.beginTime = beginTime;
                }

                public String getDelayRemark() {
                    return delayRemark;
                }

                public void setDelayRemark(String delayRemark) {
                    this.delayRemark = delayRemark;
                }

                public String getDiscount() {
                    return discount;
                }

                public void setDiscount(String discount) {
                    this.discount = discount;
                }

                public long getEndTime() {
                    return endTime;
                }

                public void setEndTime(long endTime) {
                    this.endTime = endTime;
                }

                public String getHbId() {
                    return hbId;
                }

                public void setHbId(String hbId) {
                    this.hbId = hbId;
                }

                public int getHbState() {
                    return hbState;
                }

                public void setHbState(int hbState) {
                    this.hbState = hbState;
                }

                public boolean isIsDelay() {
                    return isDelay;
                }

                public void setIsDelay(boolean isDelay) {
                    this.isDelay = isDelay;
                }

                public String getOrgLimitStr() {
                    return orgLimitStr;
                }

                public void setOrgLimitStr(String orgLimitStr) {
                    this.orgLimitStr = orgLimitStr;
                }
            }
        }
    }
}
