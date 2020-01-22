package generate;

import java.util.ArrayList;
import java.util.List;

public class ReservInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ReservInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andReservIdIsNull() {
            addCriterion("RESERV_ID is null");
            return (Criteria) this;
        }

        public Criteria andReservIdIsNotNull() {
            addCriterion("RESERV_ID is not null");
            return (Criteria) this;
        }

        public Criteria andReservIdEqualTo(String value) {
            addCriterion("RESERV_ID =", value, "reservId");
            return (Criteria) this;
        }

        public Criteria andReservIdNotEqualTo(String value) {
            addCriterion("RESERV_ID <>", value, "reservId");
            return (Criteria) this;
        }

        public Criteria andReservIdGreaterThan(String value) {
            addCriterion("RESERV_ID >", value, "reservId");
            return (Criteria) this;
        }

        public Criteria andReservIdGreaterThanOrEqualTo(String value) {
            addCriterion("RESERV_ID >=", value, "reservId");
            return (Criteria) this;
        }

        public Criteria andReservIdLessThan(String value) {
            addCriterion("RESERV_ID <", value, "reservId");
            return (Criteria) this;
        }

        public Criteria andReservIdLessThanOrEqualTo(String value) {
            addCriterion("RESERV_ID <=", value, "reservId");
            return (Criteria) this;
        }

        public Criteria andReservIdLike(String value) {
            addCriterion("RESERV_ID like", value, "reservId");
            return (Criteria) this;
        }

        public Criteria andReservIdNotLike(String value) {
            addCriterion("RESERV_ID not like", value, "reservId");
            return (Criteria) this;
        }

        public Criteria andReservIdIn(List<String> values) {
            addCriterion("RESERV_ID in", values, "reservId");
            return (Criteria) this;
        }

        public Criteria andReservIdNotIn(List<String> values) {
            addCriterion("RESERV_ID not in", values, "reservId");
            return (Criteria) this;
        }

        public Criteria andReservIdBetween(String value1, String value2) {
            addCriterion("RESERV_ID between", value1, value2, "reservId");
            return (Criteria) this;
        }

        public Criteria andReservIdNotBetween(String value1, String value2) {
            addCriterion("RESERV_ID not between", value1, value2, "reservId");
            return (Criteria) this;
        }

        public Criteria andPtNameIsNull() {
            addCriterion("PT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPtNameIsNotNull() {
            addCriterion("PT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPtNameEqualTo(String value) {
            addCriterion("PT_NAME =", value, "ptName");
            return (Criteria) this;
        }

        public Criteria andPtNameNotEqualTo(String value) {
            addCriterion("PT_NAME <>", value, "ptName");
            return (Criteria) this;
        }

        public Criteria andPtNameGreaterThan(String value) {
            addCriterion("PT_NAME >", value, "ptName");
            return (Criteria) this;
        }

        public Criteria andPtNameGreaterThanOrEqualTo(String value) {
            addCriterion("PT_NAME >=", value, "ptName");
            return (Criteria) this;
        }

        public Criteria andPtNameLessThan(String value) {
            addCriterion("PT_NAME <", value, "ptName");
            return (Criteria) this;
        }

        public Criteria andPtNameLessThanOrEqualTo(String value) {
            addCriterion("PT_NAME <=", value, "ptName");
            return (Criteria) this;
        }

        public Criteria andPtNameLike(String value) {
            addCriterion("PT_NAME like", value, "ptName");
            return (Criteria) this;
        }

        public Criteria andPtNameNotLike(String value) {
            addCriterion("PT_NAME not like", value, "ptName");
            return (Criteria) this;
        }

        public Criteria andPtNameIn(List<String> values) {
            addCriterion("PT_NAME in", values, "ptName");
            return (Criteria) this;
        }

        public Criteria andPtNameNotIn(List<String> values) {
            addCriterion("PT_NAME not in", values, "ptName");
            return (Criteria) this;
        }

        public Criteria andPtNameBetween(String value1, String value2) {
            addCriterion("PT_NAME between", value1, value2, "ptName");
            return (Criteria) this;
        }

        public Criteria andPtNameNotBetween(String value1, String value2) {
            addCriterion("PT_NAME not between", value1, value2, "ptName");
            return (Criteria) this;
        }

        public Criteria andCertNoIsNull() {
            addCriterion("CERT_NO is null");
            return (Criteria) this;
        }

        public Criteria andCertNoIsNotNull() {
            addCriterion("CERT_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCertNoEqualTo(String value) {
            addCriterion("CERT_NO =", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotEqualTo(String value) {
            addCriterion("CERT_NO <>", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoGreaterThan(String value) {
            addCriterion("CERT_NO >", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoGreaterThanOrEqualTo(String value) {
            addCriterion("CERT_NO >=", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoLessThan(String value) {
            addCriterion("CERT_NO <", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoLessThanOrEqualTo(String value) {
            addCriterion("CERT_NO <=", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoLike(String value) {
            addCriterion("CERT_NO like", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotLike(String value) {
            addCriterion("CERT_NO not like", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoIn(List<String> values) {
            addCriterion("CERT_NO in", values, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotIn(List<String> values) {
            addCriterion("CERT_NO not in", values, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoBetween(String value1, String value2) {
            addCriterion("CERT_NO between", value1, value2, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotBetween(String value1, String value2) {
            addCriterion("CERT_NO not between", value1, value2, "certNo");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`STATUS` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`STATUS` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("`STATUS` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("`STATUS` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("`STATUS` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`STATUS` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("`STATUS` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("`STATUS` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("`STATUS` like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("`STATUS` not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("`STATUS` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("`STATUS` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("`STATUS` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("`STATUS` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDepCodeIsNull() {
            addCriterion("DEP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andDepCodeIsNotNull() {
            addCriterion("DEP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andDepCodeEqualTo(String value) {
            addCriterion("DEP_CODE =", value, "depCode");
            return (Criteria) this;
        }

        public Criteria andDepCodeNotEqualTo(String value) {
            addCriterion("DEP_CODE <>", value, "depCode");
            return (Criteria) this;
        }

        public Criteria andDepCodeGreaterThan(String value) {
            addCriterion("DEP_CODE >", value, "depCode");
            return (Criteria) this;
        }

        public Criteria andDepCodeGreaterThanOrEqualTo(String value) {
            addCriterion("DEP_CODE >=", value, "depCode");
            return (Criteria) this;
        }

        public Criteria andDepCodeLessThan(String value) {
            addCriterion("DEP_CODE <", value, "depCode");
            return (Criteria) this;
        }

        public Criteria andDepCodeLessThanOrEqualTo(String value) {
            addCriterion("DEP_CODE <=", value, "depCode");
            return (Criteria) this;
        }

        public Criteria andDepCodeLike(String value) {
            addCriterion("DEP_CODE like", value, "depCode");
            return (Criteria) this;
        }

        public Criteria andDepCodeNotLike(String value) {
            addCriterion("DEP_CODE not like", value, "depCode");
            return (Criteria) this;
        }

        public Criteria andDepCodeIn(List<String> values) {
            addCriterion("DEP_CODE in", values, "depCode");
            return (Criteria) this;
        }

        public Criteria andDepCodeNotIn(List<String> values) {
            addCriterion("DEP_CODE not in", values, "depCode");
            return (Criteria) this;
        }

        public Criteria andDepCodeBetween(String value1, String value2) {
            addCriterion("DEP_CODE between", value1, value2, "depCode");
            return (Criteria) this;
        }

        public Criteria andDepCodeNotBetween(String value1, String value2) {
            addCriterion("DEP_CODE not between", value1, value2, "depCode");
            return (Criteria) this;
        }

        public Criteria andReservPhoneIsNull() {
            addCriterion("RESERV_PHONE is null");
            return (Criteria) this;
        }

        public Criteria andReservPhoneIsNotNull() {
            addCriterion("RESERV_PHONE is not null");
            return (Criteria) this;
        }

        public Criteria andReservPhoneEqualTo(String value) {
            addCriterion("RESERV_PHONE =", value, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andReservPhoneNotEqualTo(String value) {
            addCriterion("RESERV_PHONE <>", value, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andReservPhoneGreaterThan(String value) {
            addCriterion("RESERV_PHONE >", value, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andReservPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("RESERV_PHONE >=", value, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andReservPhoneLessThan(String value) {
            addCriterion("RESERV_PHONE <", value, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andReservPhoneLessThanOrEqualTo(String value) {
            addCriterion("RESERV_PHONE <=", value, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andReservPhoneLike(String value) {
            addCriterion("RESERV_PHONE like", value, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andReservPhoneNotLike(String value) {
            addCriterion("RESERV_PHONE not like", value, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andReservPhoneIn(List<String> values) {
            addCriterion("RESERV_PHONE in", values, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andReservPhoneNotIn(List<String> values) {
            addCriterion("RESERV_PHONE not in", values, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andReservPhoneBetween(String value1, String value2) {
            addCriterion("RESERV_PHONE between", value1, value2, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andReservPhoneNotBetween(String value1, String value2) {
            addCriterion("RESERV_PHONE not between", value1, value2, "reservPhone");
            return (Criteria) this;
        }

        public Criteria andAcctTypeIsNull() {
            addCriterion("ACCT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAcctTypeIsNotNull() {
            addCriterion("ACCT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAcctTypeEqualTo(String value) {
            addCriterion("ACCT_TYPE =", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeNotEqualTo(String value) {
            addCriterion("ACCT_TYPE <>", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeGreaterThan(String value) {
            addCriterion("ACCT_TYPE >", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ACCT_TYPE >=", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeLessThan(String value) {
            addCriterion("ACCT_TYPE <", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeLessThanOrEqualTo(String value) {
            addCriterion("ACCT_TYPE <=", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeLike(String value) {
            addCriterion("ACCT_TYPE like", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeNotLike(String value) {
            addCriterion("ACCT_TYPE not like", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeIn(List<String> values) {
            addCriterion("ACCT_TYPE in", values, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeNotIn(List<String> values) {
            addCriterion("ACCT_TYPE not in", values, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeBetween(String value1, String value2) {
            addCriterion("ACCT_TYPE between", value1, value2, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeNotBetween(String value1, String value2) {
            addCriterion("ACCT_TYPE not between", value1, value2, "acctType");
            return (Criteria) this;
        }

        public Criteria andVerifierIsNull() {
            addCriterion("VERIFIER is null");
            return (Criteria) this;
        }

        public Criteria andVerifierIsNotNull() {
            addCriterion("VERIFIER is not null");
            return (Criteria) this;
        }

        public Criteria andVerifierEqualTo(String value) {
            addCriterion("VERIFIER =", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierNotEqualTo(String value) {
            addCriterion("VERIFIER <>", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierGreaterThan(String value) {
            addCriterion("VERIFIER >", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierGreaterThanOrEqualTo(String value) {
            addCriterion("VERIFIER >=", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierLessThan(String value) {
            addCriterion("VERIFIER <", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierLessThanOrEqualTo(String value) {
            addCriterion("VERIFIER <=", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierLike(String value) {
            addCriterion("VERIFIER like", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierNotLike(String value) {
            addCriterion("VERIFIER not like", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierIn(List<String> values) {
            addCriterion("VERIFIER in", values, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierNotIn(List<String> values) {
            addCriterion("VERIFIER not in", values, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierBetween(String value1, String value2) {
            addCriterion("VERIFIER between", value1, value2, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierNotBetween(String value1, String value2) {
            addCriterion("VERIFIER not between", value1, value2, "verifier");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("NOTE is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("NOTE is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("NOTE =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("NOTE <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("NOTE >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("NOTE >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("NOTE <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("NOTE <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("NOTE like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("NOTE not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("NOTE in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("NOTE not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("NOTE between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("NOTE not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andReservTimeIsNull() {
            addCriterion("RESERV_TIME is null");
            return (Criteria) this;
        }

        public Criteria andReservTimeIsNotNull() {
            addCriterion("RESERV_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andReservTimeEqualTo(String value) {
            addCriterion("RESERV_TIME =", value, "reservTime");
            return (Criteria) this;
        }

        public Criteria andReservTimeNotEqualTo(String value) {
            addCriterion("RESERV_TIME <>", value, "reservTime");
            return (Criteria) this;
        }

        public Criteria andReservTimeGreaterThan(String value) {
            addCriterion("RESERV_TIME >", value, "reservTime");
            return (Criteria) this;
        }

        public Criteria andReservTimeGreaterThanOrEqualTo(String value) {
            addCriterion("RESERV_TIME >=", value, "reservTime");
            return (Criteria) this;
        }

        public Criteria andReservTimeLessThan(String value) {
            addCriterion("RESERV_TIME <", value, "reservTime");
            return (Criteria) this;
        }

        public Criteria andReservTimeLessThanOrEqualTo(String value) {
            addCriterion("RESERV_TIME <=", value, "reservTime");
            return (Criteria) this;
        }

        public Criteria andReservTimeLike(String value) {
            addCriterion("RESERV_TIME like", value, "reservTime");
            return (Criteria) this;
        }

        public Criteria andReservTimeNotLike(String value) {
            addCriterion("RESERV_TIME not like", value, "reservTime");
            return (Criteria) this;
        }

        public Criteria andReservTimeIn(List<String> values) {
            addCriterion("RESERV_TIME in", values, "reservTime");
            return (Criteria) this;
        }

        public Criteria andReservTimeNotIn(List<String> values) {
            addCriterion("RESERV_TIME not in", values, "reservTime");
            return (Criteria) this;
        }

        public Criteria andReservTimeBetween(String value1, String value2) {
            addCriterion("RESERV_TIME between", value1, value2, "reservTime");
            return (Criteria) this;
        }

        public Criteria andReservTimeNotBetween(String value1, String value2) {
            addCriterion("RESERV_TIME not between", value1, value2, "reservTime");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}