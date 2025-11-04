ll='ls -al'
ll='ls -al'
# bash에서 prompt에서 branch 보이기 (.bashrc 또는 .bash_profile)
parse_git_branch() {
  git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/(\1)/'
}

#export PS1='\e[0;33m[\t:\u \w]$ \e[m'
alias Ps2="export PS1='\e[0;33m[\W]$ '"
alias PsGit="export PS1='\[\033[32m\]\w\[\033[33m\]\$(parse_git_branch)\[\033[00m\] $ '"
