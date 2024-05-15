package com.nexmeter;

import com.pulumi.Context;
import com.pulumi.Pulumi;
import com.pulumi.aws.eks.Cluster;
import com.pulumi.aws.eks.ClusterArgs;
import com.pulumi.aws.eks.outputs.ClusterCertificateAuthority;

public class App {
    public static void main(String[] args) {
        Pulumi.run(App::stack);
    }

    public static void stack(Context ctx) {
        // Create a new EKS cluster
        var cluster = new Cluster("nexmeter", ClusterArgs.builder()
                .name("nexmeter-eks")
                .build());
        ctx.export("endpoint", cluster.endpoint());
        ctx.export("kubeconfig-certificate-authority-data", cluster.certificateAuthority()
                .applyValue(ClusterCertificateAuthority::data));
    }
}
