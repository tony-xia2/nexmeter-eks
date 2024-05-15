package com.nexmeter;


import com.pulumi.Context;
import com.pulumi.Pulumi;
import com.pulumi.aws.eks.Cluster;
import com.pulumi.kubernetes.Provider;
import com.pulumi.kubernetes.ProviderArgs;
import com.pulumi.kubernetes.helm.v3.Release;
import com.pulumi.kubernetes.helm.v3.ReleaseArgs;

public class App {
    public static void main(String[] args) {
        Pulumi.run(App::stack);
    }

    private static void stack(Context ctx) {
        final var cluster = new Cluster("eks-cluster");
        ctx.export("kubeconfig", cluster.accessConfig());
        Provider k8sProvider = new Provider("k8sProvider", ProviderArgs.builder().kubeconfig(cluster.accessConfig().toString()).build());
        new Release("strimziKafkaOperator", ReleaseArgs.builder()
                .atomic(true)
                .chart("strimzi-kafka-operator")
                .cleanupOnFail(true)
                .createNamespace(true)
                .dependencyUpdate(true)
                .build());
    }
}
