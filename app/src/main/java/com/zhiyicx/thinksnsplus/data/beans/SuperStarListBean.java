//package com.zhiyicx.thinksnsplus.data.beans;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.zhiyicx.baseproject.base.BaseListBean;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SuperStarListBean extends BaseListBean {
//
////    public List<SuperStarBean> A;
////    public List<SuperStarBean> B;
////    public List<SuperStarBean> C;
////    public List<SuperStarBean> D;
////    public List<SuperStarBean> E;
////    public List<SuperStarBean> F;
////    public List<SuperStarBean> G;
////    public List<SuperStarBean> H;
////    public List<SuperStarBean> I;
////    public List<SuperStarBean> J;
////    public List<SuperStarBean> K;
////    public List<SuperStarBean> L;
////    public List<SuperStarBean> M;
////    public List<SuperStarBean> N;
////    public List<SuperStarBean> O;
////    public List<SuperStarBean> P;
////    public List<SuperStarBean> Q;
////    public List<SuperStarBean> R;
////    public List<SuperStarBean> S;
////    public List<SuperStarBean> T;
////    public List<SuperStarBean> U;
////    public List<SuperStarBean> V;
////    public List<SuperStarBean> W;
////    public List<SuperStarBean> X;
////    public List<SuperStarBean> Y;
////    public List<SuperStarBean> Z;
////    public List<SuperStarBean> JINGHAO;
////
////    @Override
////    public int describeContents() {
////        return 0;
////    }
////
////    @Override
////    public void writeToParcel(Parcel dest, int flags) {
////        dest.writeList(this.A);
////        dest.writeList(this.B);
////        dest.writeList(this.C);
////        dest.writeList(this.D);
////        dest.writeList(this.E);
////        dest.writeList(this.F);
////        dest.writeList(this.G);
////        dest.writeList(this.H);
////        dest.writeList(this.I);
////        dest.writeList(this.J);
////        dest.writeList(this.K);
////        dest.writeList(this.L);
////        dest.writeList(this.M);
////        dest.writeList(this.N);
////        dest.writeList(this.O);
////        dest.writeList(this.P);
////        dest.writeList(this.Q);
////        dest.writeList(this.R);
////        dest.writeList(this.S);
////        dest.writeList(this.T);
////        dest.writeList(this.U);
////        dest.writeList(this.V);
////        dest.writeList(this.W);
////        dest.writeList(this.X);
////        dest.writeList(this.Y);
////        dest.writeList(this.Z);
////        dest.writeList(this.JINGHAO);
////    }
////
////    public SuperStarListBean() {
////    }
////
////    protected SuperStarListBean(Parcel in) {
////        this.A = new ArrayList<SuperStarBean>();
////        in.readList(this.A, SuperStarBean.class.getClassLoader());
////        this.B = new ArrayList<SuperStarBean>();
////        in.readList(this.B, SuperStarBean.class.getClassLoader());
////        this.C = new ArrayList<SuperStarBean>();
////        in.readList(this.C, SuperStarBean.class.getClassLoader());
////        this.D = new ArrayList<SuperStarBean>();
////        in.readList(this.D, SuperStarBean.class.getClassLoader());
////        this.E = new ArrayList<SuperStarBean>();
////        in.readList(this.E, SuperStarBean.class.getClassLoader());
////        this.F = new ArrayList<SuperStarBean>();
////        in.readList(this.F, SuperStarBean.class.getClassLoader());
////        this.G = new ArrayList<SuperStarBean>();
////        in.readList(this.G, SuperStarBean.class.getClassLoader());
////        this.H = new ArrayList<SuperStarBean>();
////        in.readList(this.H, SuperStarBean.class.getClassLoader());
////        this.I = new ArrayList<SuperStarBean>();
////        in.readList(this.I, SuperStarBean.class.getClassLoader());
////        this.J = new ArrayList<SuperStarBean>();
////        in.readList(this.J, SuperStarBean.class.getClassLoader());
////        this.K = new ArrayList<SuperStarBean>();
////        in.readList(this.K, SuperStarBean.class.getClassLoader());
////        this.L = new ArrayList<SuperStarBean>();
////        in.readList(this.L, SuperStarBean.class.getClassLoader());
////        this.M = new ArrayList<SuperStarBean>();
////        in.readList(this.M, SuperStarBean.class.getClassLoader());
////        this.N = new ArrayList<SuperStarBean>();
////        in.readList(this.N, SuperStarBean.class.getClassLoader());
////        this.O = new ArrayList<SuperStarBean>();
////        in.readList(this.O, SuperStarBean.class.getClassLoader());
////        this.P = new ArrayList<SuperStarBean>();
////        in.readList(this.P, SuperStarBean.class.getClassLoader());
////        this.Q = new ArrayList<SuperStarBean>();
////        in.readList(this.Q, SuperStarBean.class.getClassLoader());
////        this.R = new ArrayList<SuperStarBean>();
////        in.readList(this.R, SuperStarBean.class.getClassLoader());
////        this.S = new ArrayList<SuperStarBean>();
////        in.readList(this.S, SuperStarBean.class.getClassLoader());
////        this.T = new ArrayList<SuperStarBean>();
////        in.readList(this.T, SuperStarBean.class.getClassLoader());
////        this.U = new ArrayList<SuperStarBean>();
////        in.readList(this.U, SuperStarBean.class.getClassLoader());
////        this.V = new ArrayList<SuperStarBean>();
////        in.readList(this.V, SuperStarBean.class.getClassLoader());
////        this.W = new ArrayList<SuperStarBean>();
////        in.readList(this.W, SuperStarBean.class.getClassLoader());
////        this.X = new ArrayList<SuperStarBean>();
////        in.readList(this.X, SuperStarBean.class.getClassLoader());
////        this.Y = new ArrayList<SuperStarBean>();
////        in.readList(this.Y, SuperStarBean.class.getClassLoader());
////        this.Z = new ArrayList<SuperStarBean>();
////        in.readList(this.Z, SuperStarBean.class.getClassLoader());
////        this.JINGHAO = new ArrayList<SuperStarBean>();
////        in.readList(this.JINGHAO, SuperStarBean.class.getClassLoader());
////    }
////
////    public static final Parcelable.Creator<SuperStarListBean> CREATOR = new Parcelable.Creator<SuperStarListBean>() {
////        @Override
////        public SuperStarListBean createFromParcel(Parcel source) {
////            return new SuperStarListBean(source);
////        }
////
////        @Override
////        public SuperStarListBean[] newArray(int size) {
////            return new SuperStarListBean[size];
////        }
////    };
//}
