Name: t_dp_dw_hive
Version: 1.1.4
Release: 210.noarch
Summary: Taobao edition of Apache Hive
URL: %{_svn_path}@%{_svn_revision}
Group: Simba/Common
License: Commercial
Prefix: %{_prefix}

BuildArch: noarch
Requires: t_dp_hadoop >= 0.0.40

%description
%{_svn_path}
%{_svn_revision}

%prep

%build
cd $OLDPWD/../;ant clean tar

%install
mkdir -p .%{_prefix}
tar -xzvf $OLDPWD/../build/hive-%{version}.tar.gz -C .%{_prefix}

%post
rm /home/hive/hive -f
ln -sf %{_prefix}/hive-%{version} %{_prefix}/hive 

%files
%defattr(-,root,root)
%{_prefix}


%changelog
* Wed Jun 17 2009 shifeng <qingping@taobao.com> 1.0.1-1
- add samples of svn revision, release number & config file
* Tue Jun  9 2009 shifeng <qingping@taobao.com> 1.0.0-1
- do sth.
