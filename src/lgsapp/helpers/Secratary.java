package lgsapp.helpers;

public class Secratary {

    String fname;
    String lname;
    String wop;
    String office;
    String contact;
    String gender;
    String bday;
    String fappdate;
    String upgradedate;
    String retdate;
    String incdate;
    String inc;
    String beg;
    String mid;
    String end ;



    public Secratary(String fname, String lname, String wop, String office, String contact, String bday, String fappdate, String upgradedate, String retdate, String incdate, String inc, String beg, String mid, String end) {
        this.fname = fname;
        this.lname = lname;
        this.wop = wop;
        this.office = office;
        this.contact = contact;
        this.gender = gender;
        this.bday = bday;
        this.fappdate = fappdate;
        this.upgradedate = upgradedate;
        this.retdate = retdate;
        this.incdate = incdate;
        this.inc = inc;
        this.beg = beg;
        this.mid = mid;
        this.end = end;
    }



    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setWop(String wop) {
        this.wop = wop;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public void setFappdate(String fappdate) {
        this.fappdate = fappdate;
    }

    public void setUpgradedate(String upgradedate) {
        this.upgradedate = upgradedate;
    }

    public void setRetdate(String retdate) {
        this.retdate = retdate;
    }

    public void setIncdate(String incdate) {
        this.incdate = incdate;
    }

    public void setInc(String inc) {
        this.inc = inc;
    }

    public void setBeg(String beg) {
        this.beg = beg;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getWop() {
        return wop;
    }

    public String getOffice() {
        return office;
    }

    public String getContact() {
        return contact;
    }

    public String getGender() {
        return gender;
    }

    public String getBday() {
        return bday;
    }

    public String getFappdate() {
        return fappdate;
    }

    public String getUpgradedate() {
        return upgradedate;
    }

    public String getRetdate() {
        return retdate;
    }

    public String getIncdate() {
        return incdate;
    }

    public String getInc() {
        return inc;
    }

    public String getBeg() {
        return beg;
    }

    public String getMid() {
        return mid;
    }

    public String getEnd() {
        return end;
    }





    /*

        Connection con;
        PreparedStatement ps;


        public PreparedStatement addsecretary(String fname, String lname, String wop, String office, String contact, String email, String gender, String bday, String fappdate, String upgradedate, String retdate, String incdate , String inc, String beg, String mid, String end, FileInputStream imagefile, String curyr)  {
        con = DbConnect.getConnection();

        String query = "INSERT INTO `secrataries`( `fname`, `lname`, `wop`, `office`, `contact`, `email`, `gender`, `bday`, `fappdate`, `upgdate`, `retdate`, `incdate`, `salinc`, `yrbeg`, `yrmid`, `yrend`, `imageid`, `curyr`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";



        try {
            ps = con.prepareStatement(query);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, wop);
            ps.setString(4, office);
            ps.setString(5, contact);
            ps.setString(6, email);
            ps.setString(7, gender);
            ps.setString(8, bday);
            ps.setString(9, fappdate);
            ps.setString(10, upgradedate);
            ps.setString(11, retdate);
            ps.setString(12, incdate);
            ps.setString(13, inc);
            ps.setString(14, beg);
            ps.setString(15, mid);
            ps.setString(16, end);
            ps.setBinaryStream(17,imagefile);
            ps.setString(18, curyr);




        } catch (SQLException e) {
            e.printStackTrace();
        }



        return ps;
    }*/

}
