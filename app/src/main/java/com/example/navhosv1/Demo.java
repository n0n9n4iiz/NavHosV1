package com.example.navhosv1;

public class Demo {
    public static class Contributor {
        final int p_id;
        final String p_name;
        final int isRoom;
        final String p_detail_for_direction;

        public Contributor(int p_id, String p_name,int isRoom,String p_detail_for_direction) {
            this.p_id = p_id;
            this.p_name = p_name;
            this.isRoom = isRoom;
            this.p_detail_for_direction=p_detail_for_direction;
        }

        public int getP_id() {
            return p_id;
        }

        public String getP_name() {
            return p_name;
        }
        public int getIsRoom() {
            return isRoom;
        }

        public String getP_detail_for_direction() {
            return p_detail_for_direction;
        }


    }
}
